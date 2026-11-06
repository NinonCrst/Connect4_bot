import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Sauvegarde {
    private int firstPlayer;
    private int ligne;
    private int col;
    private int index;
    private int currentTurn;
    private int tourJoueur;
    private int mode;
    private int confiance;
    private boolean statut;
    private int[][] grille;
    private int[][] sym;
    private boolean[][] grilleWin;
    private List<Coup> coups;
    private List<Coup> coupsAnnules;

    public Sauvegarde(String path){
        load(path);
    }

    public Sauvegarde(ModelConnect4 partie){
        this.firstPlayer = partie.getPlayer();
        this.ligne = partie.getLigne();
        this.col = partie.getCol();
        this.index = partie.getIndex();
        this.currentTurn = partie.getCurrentTurn();
        this.tourJoueur = partie.getPlayerTurn();
        this.mode = partie.getCurrentModeG();
        this.confiance = partie.getConfiance();
        this.statut = partie.getInGame();
        this.grille = partie.getGrille();
        this.grilleWin = partie.getGrilleWin();
        this.coups = partie.getCoups();
    }

    public Sauvegarde(int firstPlayer, int ligne, int col, int index, int currentTurn, int tourJoueur, int mode, int confiance, boolean statut, int[][] grille, boolean[][] grilleWin, List<Coup> coups){
        this.firstPlayer = firstPlayer;
        this.ligne = ligne;
        this.col = col;
        this.index = index;
        this.currentTurn = currentTurn;
        this.tourJoueur = tourJoueur;
        this.mode = mode;
        this.confiance = confiance;
        this.statut = statut;
        this.grille = grille;
        this.grilleWin = grilleWin;
        this.coups = coups;
        this.coupsAnnules = new ArrayList<>();
    }

    public Sauvegarde(int firstPlayer, int ligne, int col, int index, int currentTurn, int tourJoueur, int mode, int confiance, boolean statut, int[][] grille, int[][] sym, boolean[][] grilleWin, List<Coup> coups){
        this.firstPlayer = firstPlayer;
        this.ligne = ligne;
        this.col = col;
        this.index = index;
        this.currentTurn = currentTurn;
        this.tourJoueur = tourJoueur;
        this.mode = mode;
        this.confiance = confiance;
        this.statut = statut;
        this.grille = grille;
        this.sym = sym;
        this.grilleWin = grilleWin;
        this.coups = coups;
    }

    public void load(String path){
        try (BufferedReader read = new BufferedReader(new FileReader(path))){
            this.firstPlayer = Integer.parseInt(read.readLine());
            this.ligne = Integer.parseInt(read.readLine());
            this.col = Integer.parseInt(read.readLine());
            this.index = Integer.parseInt(read.readLine());
            this.currentTurn = Integer.parseInt(read.readLine());
            this.tourJoueur = Integer.parseInt(read.readLine());
            this.mode = Integer.parseInt(read.readLine());
            this.confiance = Integer.parseInt(read.readLine());
            this.statut = Boolean.parseBoolean(read.readLine());

            this.grille = new int[ligne][col];
            for(int i = 0; i<ligne; i++){
                String rangee = read.readLine();
                for(int j =0; j<col; j++){
                    char caseG = rangee.charAt(j);
                    grille[i][j] = caseG - '0';
                }
            }

            this.grilleWin = new boolean[ligne][col];
            for(int i = 0; i<ligne; i++){
                String rangee = read.readLine();
                String[] values = rangee.split(" ");
                for(int j =0; j<col; j++){
                    grilleWin[i][j] = Boolean.parseBoolean(values[j]);
                }
            }

            this.coups = new ArrayList<>();
            int countCoups = 0;
            for(int i = 0; i<ligne; i++){
                for(int j =0; j<col; j++){
                    if(grille[i][j] != 0) countCoups++;
                }
            }

            for(int k = 0; k<countCoups; k++){
                String rangee = read.readLine();
                String[] values = rangee.split(" ");
                int ligneV = Integer.parseInt(values[0]);
                int colV = Integer.parseInt(values[1]);
                int playerV = Integer.parseInt(values[2]);
                Coup action = new Coup(ligneV, colV, playerV);
                coups.add(action);
            }

            this.coupsAnnules = new ArrayList<>();
            String lastRangee;
            while((lastRangee = read.readLine()) != null){
                String[] values = lastRangee.split(" ");
                int ligneV = Integer.parseInt(values[0]);
                int colV = Integer.parseInt(values[1]);
                int playerV = Integer.parseInt(values[2]);
                Coup action = new Coup(ligneV, colV, playerV);
                coupsAnnules.add(action);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public int getFirstPlayerSave(){
        return this.firstPlayer;
    }

    public int getLigneSave(){
        return this.ligne;
    }

    public int getColSave(){
        return this.col;
    }

    public int getIndexSave(){
        return this.index;
    }

    public int getCurrentTurnSave(){
        return this.currentTurn;
    }

    public int getTourJoueurSave(){
        return this.tourJoueur;
    }

    public int getModeSave(){
        return this.mode;
    }

    public boolean getStatutSave(){
        return this.statut;
    }

    public int[][] getGrilleSave(){
        return this.grille;
    }

    public boolean[][] getGrilleWinSave(){
        return this.grilleWin;
    }

    public List<Coup> getCoupsSave(){
        return this.coups;
    }

    public List<Coup> getCoupsAnnulesSave(){
        return this.coupsAnnules;
    }

    public int[][] getSym(){
        return this.sym;
    }

    public double getConfiance(){
        return this.confiance;
    }
}