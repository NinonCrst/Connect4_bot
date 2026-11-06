import java.util.Random;

public class BGABot {

    private final BgaConnection connection;
    private final ModelConnect4 model;
    private final AlgoMiniMax miniMax;
    private final Random rand;

    private String tableId;
    private int monNumeroJoueur = -1;
    private BGADomScraper scraper;

    private static final int ATTENTE_NOUVELLE_PARTIE_MS = 20 * 60 * 1000;
    private static final int POLLING_NOUVELLE_PARTIE_MS = 3000;

    public BGABot(ModelConnect4 model){
        this.connection = new BgaConnection();
        this.model = model;
        this.miniMax = new AlgoMiniMax();
        this.rand = new Random();
    }

    public void run() throws Exception {
        var driver = connection.createDriver();
        try {
            System.out.println("[Bot] Ouverture du navigateur...");
            connection.connectBga(driver);
            System.out.println("[Bot] ✅ Connexion BGA réussie.\n");

            while(true){
                tableId = attendreNouvellePartie(driver);
                if(tableId == null){
                    System.out.println("[Bot] ⏱ Timeout de 20 minutes atteint sans nouvelle partie. Arrêt.");
                    break;
                }
                scraper = new BGADomScraper(driver);
                Thread.sleep(3000);
                monNumeroJoueur = scraper.detecterMonNumeroJoueur();
                boucleDeJeu();
            }

        } finally{
            System.out.println("[Bot] Fermeture du navigateur dans 5 secondes...");
            Thread.sleep(5000);
            driver.quit();
        }
    }

    /**
     * @return
     */
    private String attendreNouvellePartie(org.openqa.selenium.WebDriver driver) throws InterruptedException {
        long debut = System.currentTimeMillis();
        String dernierTableId = tableId; 
        while(System.currentTimeMillis() - debut < ATTENTE_NOUVELLE_PARTIE_MS){
            String url = driver.getCurrentUrl();

            if(url.contains("connectfour") || url.contains("connect4")){
                String tid = BgaConnection.extractTableId(url);
                if(tid != null && !tid.equals(dernierTableId)){
                    return tid;
                }
                if(tid != null && dernierTableId == null){
                    return tid;
                }
            }

            long ecoule = System.currentTimeMillis() - debut;
            long restant = (ATTENTE_NOUVELLE_PARTIE_MS - ecoule) / 1000 / 60;
            if(ecoule % 60000 < POLLING_NOUVELLE_PARTIE_MS){
            }
            Thread.sleep(POLLING_NOUVELLE_PARTIE_MS);
        }

        return null;
    }

    private void boucleDeJeu() throws InterruptedException{
        System.out.println("[Bot] ▶  Boucle de jeu démarrée !\n");
        int tourJoue = 0;
        int nbPionsPrecedent = -1; 

        while(true){
            if(scraper.partieTerminee()){
                System.out.println("[Bot] 🏁 Partie terminée !");
                break;
            }

            if(!scraper.estMonTour(monNumeroJoueur)){
                System.out.print(".");
                Thread.sleep(BGAConfig.POLLING_INTERVAL_MS);
                continue;
            }
            tourJoue++;

            int[][] grille = null;
            int tentatives = 0;
            while(tentatives < 20){
                Thread.sleep(400);
                int lastMove = scraper.getLastMove();
                if(nbPionsPrecedent < 0 || lastMove > nbPionsPrecedent){
                    break;
                }
                tentatives++;
            }
            grille = scraper.lireGrille();
            if(grille == null) grille = new int[BGAConfig.LIGNES][BGAConfig.COLS];

            long t0  = System.currentTimeMillis();
            int  col = calculerMeilleurCoup(grille);
            long dt  = System.currentTimeMillis() - t0;

            if(BGAConfig.DEBUG_SCORES) {
                int[] scores = miniMax.getScoreTab();
                if(scores != null){
                    StringBuilder sb = new StringBuilder("[Bot] Scores : ");
                    for(int i = 0; i < scores.length; i++)
                        sb.append("col").append(i + 1).append("=").append(scores[i]).append("  ");
                    System.out.println(sb);
                }
            }

            if(col < 0){
                System.err.println("[Bot] Aucune colonne disponible — partie bloquée ?");
                break;
            }

            int delai = BGAConfig.DELAI_MIN_MS + rand.nextInt(BGAConfig.DELAI_ALEATOIRE_MS);
            Thread.sleep(delai);

            boolean ok = scraper.jouerColonne(col);
            if(ok){
                Thread.sleep(1200);
                Thread.sleep(800);
                nbPionsPrecedent = scraper.getLastMove();
            } else{
                System.err.println("[Bot] ⚠️  Échec du coup.");
            }

            Thread.sleep(500);
        }
    }

    private int calculerMeilleurCoup(int[][] grille){
        return miniMax.calculCoup(
            grille,
            model.getProfondeur(),
            model.getLigne(),
            model.getCol(),
            monNumeroJoueur
        );
    }
}
