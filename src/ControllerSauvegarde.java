import java.io.File;
import java.util.List;

import javax.swing.*;

public class ControllerSauvegarde {
    private ModelConnect4 partie;
    private VueBoard vBoard;
    private Configuration config;
    private VueData vData;
    private VueOutilsBdd vOutilsBdd;

    private Sauvegarde save;
    private SauvegardeDatabase saveData;

    private int indexChoosen;

    public ControllerSauvegarde(ModelConnect4 partie, VueBoard board, Configuration config, VueData data, VueOutilsBdd vOutilsBdd){
        this.partie = partie;
        this.vBoard = board;
        this.config = config;
        this.vData = data;
        this.vOutilsBdd = vOutilsBdd;

        vBoard.getOpen().addActionListener(e -> saveOpen());
        vBoard.getSave().addActionListener(e -> savePartie());
        vBoard.getOpenBdd().addActionListener(e -> saveOpenBdd());
        vBoard.getOutilsBdd().addActionListener(e -> saveOpenOutilsBdd());

        vData.getOuvrir().addActionListener(e -> ouvrirSauvegarde());
        vData.getAnnuler().addActionListener(e -> vData.dispose());

        vOutilsBdd.getOuvrir().addActionListener(e -> ouvrirSauvegardeOutils());
        vOutilsBdd.getAnnuler().addActionListener(e -> vOutilsBdd.dispose());
    }

    public void saveOpen(){
        JFileChooser fichierChoix = vBoard.getOpenFileSave();
        int open = fichierChoix.showOpenDialog(null);

        if(open == JFileChooser.APPROVE_OPTION){
            File saveFile = fichierChoix.getSelectedFile();
            save = new Sauvegarde(saveFile.getAbsolutePath());
            partie.openSave(save);
            vBoard.updateConfig(save.getFirstPlayerSave(), save.getLigneSave(), save.getColSave(), save.getGrilleSave(), save.getGrilleWinSave());
            vBoard.updateTurn(save.getCurrentTurnSave());
            vBoard.updateIndications(save.getTourJoueurSave());
            vBoard.updateModeJeu(save.getModeSave());
        }
    }

    public void savePartie(){
        Sauvegarde saveGame = partie.setSave();
        saveData = new SauvegardeDatabase();
        boolean success = saveData.saveDatabase(saveGame);

        if(success){
            config.setIndex(saveGame.getIndexSave()+1);
            partie.setIndex(saveGame.getIndexSave()+1);
            config.save("config.txt");
        }
    }

    public void saveOpenBdd(){
        saveData = new SauvegardeDatabase();
        vData.updateAllSaves(saveData.openDatabase());
        vData.setController(this);
        vData.pack();
        vData.setLocationRelativeTo(null);
        vData.setVisible(true);
    }

    public void saveOpenOutilsBdd(){
        saveData = new SauvegardeDatabase();
        vOutilsBdd.updateAllSaves(saveData.openDatabaseOutils());
        vOutilsBdd.setController(this);
        vOutilsBdd.pack();
        vOutilsBdd.setLocationRelativeTo(null);
        vOutilsBdd.setVisible(true);
    }

    public void ouvrirSauvegarde(){
        saveData = new SauvegardeDatabase();
        System.out.println("index dans controlleur : "+indexChoosen);
        save = saveData.openData(indexChoosen);
        System.out.println("Bouton ouvrir appuyé");

        if(save != null){
            System.out.println("Sauvegarde s'ouvre");
            partie.openSave(save);
            vBoard.updateConfig(save.getFirstPlayerSave(), save.getLigneSave(), save.getColSave(), save.getGrilleSave(), save.getGrilleWinSave());
            vBoard.updateTurn(save.getCurrentTurnSave());
            vBoard.updateIndications(save.getTourJoueurSave());
            vBoard.updateModeJeu(save.getModeSave());
        }

        vData.dispose();
    }

    public void ouvrirSauvegardeOutils(){
        saveData = new SauvegardeDatabase();
        List<Sauvegarde> saveCS = saveData.openDataOutilsBdd(indexChoosen);

        if((saveCS.get(0) != null)&&(saveCS.get(1) != null)&&(saveCS != null)){
            vOutilsBdd.getPartieCano().openSave(saveCS.get(0));
            vOutilsBdd.getPartieSym().openSave(saveCS.get(1));
            vOutilsBdd.updateGrillesAffichage();
        }
    }

    public void setIndexChoosen(int id){
        this.indexChoosen = id;
    }
}