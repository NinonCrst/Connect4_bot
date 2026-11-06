public class ControllerStart {
    private VueBoard vBoard;
    private ModelConnect4 partie;
    private VueWinner vWinner;

    public ControllerStart(VueBoard board, ModelConnect4 model, VueWinner winner){
        this.partie = model;
        this.vBoard = board;
        this.vWinner = winner;

        this.vBoard.getCvc().addActionListener(e -> startGame(0));
        this.vBoard.getIavia().addActionListener(e -> startGame(1));
        this.vBoard.getIaviaBdd().addActionListener(e -> startGame(2));
        this.vBoard.getCvp().addActionListener(e -> startGame(3));
        this.vBoard.getIavp().addActionListener(e -> startGame(4));
        this.vBoard.getIavpBdd().addActionListener(e -> startGame(5));
        this.vBoard.getPvp().addActionListener(e -> startGame(6));
        this.vBoard.getRestart().addActionListener(e -> {
            if(partie.getCurrentModeG() != -1) startGame(partie.getCurrentModeG());
        }); 
    }

    private void startGame(int modeJeu){
        partie.startPlay(modeJeu, this.vBoard, this.vWinner);
    }
}