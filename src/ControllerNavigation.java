public class ControllerNavigation {
    private VueBoard vBoard;
    private VueParametre vParametre;

    private VueParametre vParametre2;

    private VueWinner vWinner;

    public ControllerNavigation(VueBoard board, VueParametre parametre){
        this.vBoard = board;
        this.vParametre = parametre;

        this.vBoard.getParametres().addActionListener(e -> openVueParametre());
    }

    public void openVueParametre(){
        this.vParametre.setVisible(true);
        this.vParametre.pack();
        this.vParametre.setLocationRelativeTo(null);
    }

    public ControllerNavigation(VueParametre parametre){
        this.vParametre2 = parametre;

        this.vParametre2.getQuit().addActionListener(e -> closeVueParametre());
    }

    public void closeVueParametre(){
        this.vParametre2.dispose();
    }

    public ControllerNavigation(VueWinner winner){
        this.vWinner = winner;

        this.vWinner.getNext().addActionListener(e -> openVueMenuCloseVueWinner());
    }

    public void openVueMenuCloseVueWinner(){
        this.vWinner.dispose();
    }
}