import java.sql.Connection;

public class Main {
    public static void main(String[] args){
        try(Connection co = DataBase.getConnexion()){
            System.out.println("Base de donnée connectée");
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
        Configuration config = new Configuration("config.txt");

        ModelConnect4 partie = new ModelConnect4(config.getFirstPlayer(), config.getLigne(), config.getCol(), config.getIndex());

        ControllerModel playControl = new ControllerModel(partie);

        VueBoard vBoard = new VueBoard(partie);
        vBoard.pack();
        vBoard.setLocationRelativeTo(null);
        vBoard.setVisible(true);

        VueParametre vParametre = new VueParametre(config);
        vParametre.setUndecorated(true);

        VueWinner vWinner = new VueWinner(0);
        vWinner.pack();
        vWinner.setLocationRelativeTo(null);

        VueData vData = new VueData();
        VueOutilsBdd vOutilsBdd = new VueOutilsBdd();

        ControllerConfig configChanges = new ControllerConfig(vParametre, partie, config, vBoard);
        ControllerSauvegarde save = new ControllerSauvegarde(partie, vBoard, config, vData, vOutilsBdd);

        ControllerStart start = new ControllerStart(vBoard, partie, vWinner);
        
        ControllerNavigation openVueParametre = new ControllerNavigation(vBoard, vParametre);
        ControllerNavigation closeVueParametre = new ControllerNavigation(vParametre);
        ControllerNavigation closeVueWinner = new ControllerNavigation(vWinner);

        ControllerGamePlay gamePlayC = new ControllerGamePlay(partie, vBoard, vOutilsBdd);
    }
}