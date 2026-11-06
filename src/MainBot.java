public class MainBot {

    public static void main(String[] args) throws Exception {
        System.out.println(System.getenv("BGA_ID"));

        // Validation de la configuration
        try {
            BGAConfig.validate();
        } catch (IllegalStateException e) {
            System.err.println("Stop " + e.getMessage());
            System.err.println("   → Ouvrez BGAConfig.java et remplissez EMAIL et PASSWORD.");
            System.exit(1);
        }

        // Créer le modèle Minimax
        // MY_PLAYER_NUMBER ici n'est qu'un fallback — le bot le détecte en jeu
        ModelConnect4 model = new ModelConnect4(
            BGAConfig.MY_PLAYER_NUMBER,
            BGAConfig.LIGNES,
            BGAConfig.COLS,
            BGAConfig.PROFONDEUR_MINIMAX
        );

        // Créer et lancer le bot
        BGABot bot = new BGABot(model);

        // Hook Ctrl+C propre
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
            System.out.println("\n[Bot] Arrêt demandé (Ctrl+C). Au revoir !")
        ));

        bot.run();
    }
}