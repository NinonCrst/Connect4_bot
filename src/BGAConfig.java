public class BGAConfig {
    public static final String EMAIL = System.getenv("BGA_ID");
    public static final String PASSWORD = System.getenv("BGA_PASSWORD");

    public static final String EDGE_DRIVER_PATH = "C:\\selenium\\msedgedriver.exe";
    public static final int MY_PLAYER_NUMBER = 1;
    public static final int PROFONDEUR_MINIMAX = 7;
    public static final int LIGNES = 9;
    public static final int COLS = 9;

    public static final int DELAI_MIN_MS = 0;
    public static final int DELAI_ALEATOIRE_MS = 1;
    public static final int POLLING_INTERVAL_MS = 200;
    public static final int SELENIUM_TIMEOUT_S = 5;

    public static final boolean DEBUG_GRILLE = false;
    public static final boolean DEBUG_SCORES = false;
    public static final boolean DEBUG_DOM = false; 

    public static void validate(){
        if(EMAIL.isEmpty() || PASSWORD.isEmpty()) throw new IllegalStateException("EMAIL / PASSWORD non configurés dans BGAConfig.java");
        System.out.println("[Config] Email : " + EMAIL);
        System.out.println("[Config] Minimax : profondeur " + PROFONDEUR_MINIMAX);
        System.out.println("[Config] Grille : " + LIGNES + " x " + COLS);
    }
}