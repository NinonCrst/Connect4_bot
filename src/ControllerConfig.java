public class ControllerConfig{
    private VueParametre vParametre;
    private ModelConnect4 partie;
    private Configuration config;
    private VueBoard vBoard;

    public ControllerConfig(VueParametre parametre, ModelConnect4 partie, Configuration config, VueBoard board){
        this.vParametre = parametre;
        this.partie = partie;
        this.config = config;
        this.vBoard = board;

        this.vParametre.getApply().addActionListener(e ->{
            if(!partie.getInGame()) setChanges();
        });
    }

    public void setChanges(){
        int firstPlayer = vParametre.getColor();
        int ligne = vParametre.getLigne();
        int col = vParametre.getCol();

        this.config.setFirstPlayer(firstPlayer);
        this.config.setLigne(ligne);
        this.config.setCol(col);

        this.config.save("config.txt");

        this.partie.setChanges(firstPlayer, ligne, col);

        this.vBoard.updateConfig(firstPlayer, ligne, col, partie.getGrille(), partie.getGrilleWin());
    }
}