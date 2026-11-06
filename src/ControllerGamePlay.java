public class ControllerGamePlay {
    private ModelConnect4 partie;
    private VueBoard vBoard;
    private VueOutilsBdd vOutilsBdd;

    public ControllerGamePlay(ModelConnect4 partie, VueBoard board, VueOutilsBdd outils){
        this.partie = partie;
        this.vBoard = board;
        this.vOutilsBdd = outils;

        vBoard.getPlay().addActionListener(e -> {
            if(partie.getCurrentModeIA()) playGame();
        });
        vBoard.getPause().addActionListener(e -> {
            if(partie.getCurrentModeIA()) pauseGame();
        });
        vBoard.getStop().addActionListener(e -> {
            if(partie.getCurrentModeIA()) pauseGame();
        });
        vBoard.getBack().addActionListener(e -> backGame());
        vBoard.getForward().addActionListener(e -> frowardGame());

        vBoard.getSetProf().addActionListener(e -> modifProf());
        vBoard.getScore().addActionListener(e ->{
            if(partie.getCurrentModeG() == 1) printScore();
        });

        vOutilsBdd.getBack().addActionListener(e -> backGameOutils());
        vOutilsBdd.getForward().addActionListener(e -> forwardGameOutils());
    }

    public void playGame(){
        this.partie.removePause();
    }

    public void pauseGame(){
        this.partie.setPause();
    }

    public void backGame(){
        this.partie.backCoup();
        this.vBoard.updateGrille(partie.getCol(), partie.getLigne(), partie.getGrille(), partie.getGrilleWin());
        this.vBoard.updateTurn(partie.getCurrentTurn());
    }

    public void frowardGame(){
        this.partie.forwardCoup();
        this.vBoard.updateGrille(partie.getCol(), partie.getLigne(), partie.getGrille(), partie.getGrilleWin());
        this.vBoard.updateTurn(partie.getCurrentTurn());
    }

    public void modifProf(){
        int newProf = vBoard.getProf();
        if(!partie.getInGame()) partie.setProfondeur(newProf);
    }

    public void printScore(){
        vBoard.setScoreVisibles();
    }

    public void backGameOutils(){
        vOutilsBdd.getPartieCano().backCoup();
        vOutilsBdd.getPartieSym().backCoup();
        vOutilsBdd.updateGrillesAffichage();
    }

    public void forwardGameOutils(){
        vOutilsBdd.getPartieCano().forwardCoup();
        vOutilsBdd.getPartieSym().forwardCoup();
        vOutilsBdd.updateGrillesAffichage();
    }
}