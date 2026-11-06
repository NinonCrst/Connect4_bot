import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VueBoard extends JFrame {
    //Variables utilisation modèle
    private int ligne;
    private int col;
    private int player;

    private int[][] plateau;
    private boolean[][] plateauWin;

    //Variables vue en lien avec modèle
    private DrawCase[][] tableau;
    private ControllerModel control;
    private boolean clic = false;
    private int size;

    private boolean scoreVisibles = false;

    //Main 
    private JPanel main = new JPanel();

    //rightPanel
    private JPanel rightPanel = new JPanel();

        //topPanel
    private JPanel topPanel = new JPanel();
            //Panel boutons back et forward
    private JPanel nav = new JPanel();
            //Panel boutons restart et stop
    private JPanel restartStop = new JPanel();
            //Panel boutons play et pause
    private JPanel playPause = new JPanel();
            //Panel de gestion de la profondeur
    private JPanel gProf = new JPanel();
            //Panel affichage mode de jeu
    private JPanel mode = new JPanel();

            //Séparateurs de topPanel entre les panels de boutons
    private JSeparator sepTopH1 = new JSeparator(SwingConstants.VERTICAL);
    private JSeparator sepTopH2 = new JSeparator(SwingConstants.VERTICAL);

            //Boutons back et forward
    private JButton back;
    private JButton forward;

            //Boutons restart et stop
    private JButton restart;
    private JButton stop;

            //Boutons play et pause
    private JButton play;
    private JButton pause;

            //Gestion de la profondeur
    private JLabel profQ;
    private final Integer[] listeProf = {0, 1, 2, 3, 4, 5, 6, 7};
    private JComboBox<Integer> prof;
    private JButton setProf;

            //Label mode
    private JLabel modeJeu;

        //centerPanel
    private JPanel centerPanel = new JPanel();
            //panel du haut
    private JPanel topCenterPanel = new JPanel();
            //panel grille
    private JPanel grille = new JPanel();
            //panel score
    private JPanel printScore = new JPanel();

                //panel infos de topCenterPanel
    private JPanel infos = new JPanel();
                //panel de numérotation des colonnes de topCenterPanel
    private JPanel numCol = new JPanel();

            //Labels turn et indications
    private JLabel turn;
    private JLabel indications;

    //leftPanel
    private JPanel leftPanel = new JPanel();
        //scores panel
    private JPanel affichScore = new JPanel();
        //menu panel
    private JPanel menu = new JPanel();
        //parametres panel
    private JPanel param = new JPanel();
        //save panel
    private JPanel saves = new JPanel();

            //Bouton score
    private JButton score;

            //Bouton cvc, iavia, cvp, iavp et pvp
    private JButton cvc;
    private JButton iavia;
    private JButton iaviabdd;
    private JButton cvp;
    private JButton iavp;
    private JButton iavpbdd;
    private JButton pvp;

            //Bouton param
    private JButton parametres;

            //Bouton save, open et review
    private JButton save;
    private JButton open;
    private JButton openBdd;
    private JButton review;
    private JButton outilsBdd;

        //Séparateurs leftPanel
    private JSeparator sepLeft1 = new JSeparator(SwingConstants.HORIZONTAL);
    private JSeparator sepLeft2 = new JSeparator(SwingConstants.HORIZONTAL);
    private JSeparator sepLeft3 = new JSeparator(SwingConstants.HORIZONTAL);

    private JFileChooser openFileSave = new JFileChooser();

    public VueBoard (ModelConnect4 partie) {
        this.setTitle("Connect4");
        this.ligne = partie.getLigne();;
        this.col = partie.getCol();
        this.player = partie.getPlayer();
        this.plateau = partie.getGrille();
        this.plateauWin = partie.getGrilleWin();
        tableau = new DrawCase[ligne][col];

        control = new ControllerModel(partie);
        FileNameExtensionFilter filtreType = new FileNameExtensionFilter("Fichiers texte", "txt");
        openFileSave.setFileFilter(filtreType);
        openFileSave.setAcceptAllFileFilterUsed(false);

        //Dessin du main panel
        main.setLayout(new BorderLayout());
        main.setBorder(new MatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));

        //Dessin du rightPanel
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(new MatteBorder(0, 2, 0, 0, Color.BLACK));

            //Dessin du topPanel
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(new Color(222, 248, 249));
        topPanel.setBorder((new EmptyBorder(10,0,10,0)));

                //Dessin du panel nav
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBorder(new EmptyBorder(0,20,0,20));
        nav.setOpaque(false);

                    //Bouton back
        back = new JButton("Back");
        back.setFont(new Font("Georgia", Font.PLAIN, 14));
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //Bouton forward
        forward = new JButton("Forward");
        forward.setFont(new Font("Georgia", Font.PLAIN, 14));
        forward.setAlignmentX(Component.CENTER_ALIGNMENT);

        back.setMaximumSize(forward.getPreferredSize());

                    //Ajout des boutons dans nav
        nav.add(Box.createVerticalGlue());
        nav.add(back);
        nav.add(Box.createVerticalStrut(10));
        nav.add(forward);
        nav.add(Box.createVerticalGlue());
        topPanel.add(nav);

                //Dessin du premier séparateur de topPanel
        sepTopH1.setMinimumSize(new Dimension(1,2));
        sepTopH1.setBackground(Color.GRAY);
        topPanel.add(sepTopH1);

                //Dessin panel restartStop
        restartStop.setLayout(new BoxLayout(restartStop, BoxLayout.Y_AXIS));
        restartStop.setBorder(new EmptyBorder(0,20,0,20));
        restartStop.setOpaque(false);

                    //Bouton restart
        restart = new JButton("Restart");
        restart.setFont(new Font("Georgia", Font.PLAIN, 14));
        restart.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //Bouton stop
        stop = new JButton("Stop");
        stop.setFont(new Font("Georgia", Font.PLAIN, 14));
        stop.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        stop.setMaximumSize(restart.getPreferredSize());

                    //Ajout des boutons dans restartStop
        restartStop.add(Box.createVerticalGlue());
        restartStop.add(restart);
        restartStop.add(Box.createVerticalStrut(10));
        restartStop.add(stop);
        restartStop.add(Box.createVerticalGlue());
        topPanel.add(restartStop);

                //Dessin du second séparateur de topPanel
        sepTopH2.setMinimumSize(new Dimension(1,2));
        sepTopH2.setBackground(Color.GRAY);
        topPanel.add(sepTopH2);

                //Dessin du panel playPause
        playPause.setLayout(new BoxLayout(playPause, BoxLayout.Y_AXIS));
        playPause.setBorder(new EmptyBorder(0,20,0,20));
        playPause.setOpaque(false);

                    //Bouton play
        play = new JButton("Play");
        play.setFont(new Font("Georgia", Font.PLAIN, 14));
        play.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //Bouton pause
        pause = new JButton("Pause");
        pause.setFont(new Font("Georgia", Font.PLAIN, 14));
        pause.setAlignmentX(Component.CENTER_ALIGNMENT);

        play.setMaximumSize(pause.getPreferredSize());

                    //Ajout des boutons dans panel playPause
        playPause.add(Box.createVerticalGlue());
        playPause.add(play);
        playPause.add(Box.createVerticalStrut(10));
        playPause.add(pause);
        playPause.add(Box.createVerticalGlue());
        topPanel.add(playPause);

                //Dessin du panel de gestion de la profondeur
        gProf.setLayout(new BoxLayout(gProf, BoxLayout.Y_AXIS));
        gProf.setBorder(new EmptyBorder(0, 20, 0, 20));
        gProf.setOpaque(false);

                    //Label profondeur
        profQ = new JLabel("Choisissez la profondeur : ");
        profQ.setFont(new Font("Georgia", Font.PLAIN, 14));
        profQ.setAlignmentX(Component.CENTER_ALIGNMENT);

        prof = new JComboBox<>(listeProf);
        prof.setFont(new Font("Georgia", Font.PLAIN, 14));
        prof.setAlignmentX(Component.CENTER_ALIGNMENT);
        prof.setBackground(new Color(245, 245, 245));
        prof.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if(isSelected){
                    comp.setBackground(Color.BLACK);
                    comp.setForeground(Color.WHITE);
                } 

				else {
                    comp.setBackground(Color.WHITE);
                    comp.setForeground(Color.BLACK);
                }
                return comp;
            }
        });
        prof.setEditable(true);
        prof.setSelectedItem(partie.getProfondeur());
        prof.getEditor().getEditorComponent().setBackground(new Color(241,250,247));

        setProf = new JButton("Apply");
        setProf.setFont(new Font("Georgia", Font.PLAIN, 14));
        setProf.setAlignmentX(Component.CENTER_ALIGNMENT);

        gProf.add(Box.createVerticalGlue());
        gProf.add(profQ);
        gProf.add(Box.createVerticalStrut(10));
        gProf.add(prof);
        gProf.add(Box.createVerticalStrut(10));
        gProf.add(setProf);
        gProf.add(Box.createVerticalGlue());
        topPanel.add(gProf);

                //Ajout séparation dans topPanel
        topPanel.add(Box.createHorizontalStrut(40));

                //Dessin du panel mode
        mode.setLayout(new BoxLayout(mode, BoxLayout.Y_AXIS));
        mode.setBorder(new EmptyBorder(0, 10, 5, 10));
        mode.setOpaque(false);

                    //Label modeJeu
        modeJeu = new JLabel("Choisissez votre mode de jeu ");
        modeJeu.setFont(new Font("Georgia", Font.PLAIN, 14));
        modeJeu.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //Ajout de la label dans panel mode
        mode.add(Box.createVerticalGlue());
        mode.add(modeJeu);
        topPanel.add(mode);

            //Ajout du topPanel dans rightPanel
        rightPanel.add(topPanel, BorderLayout.NORTH);

            //Dessin de centerPanel
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

                //Dessin du topCenterPanel
        topCenterPanel.setLayout(new BoxLayout(topCenterPanel, BoxLayout.Y_AXIS));
        topCenterPanel.setOpaque(false);

                    //Dessin du panel info
        infos.setLayout(new BoxLayout(infos, BoxLayout.X_AXIS));
        infos.setBackground(Color.WHITE);
        infos.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));

                        //Label turn
        turn = new JLabel(" Tour : "+this.colorTurn());
        turn.setFont(new Font("Georgia", Font.PLAIN, 14));
        turn.setBorder(new EmptyBorder(5,0,5,0));
        infos.add(turn);

        infos.add(Box.createHorizontalGlue());

                        //Label indications
        indications = new JLabel("");
        indications.setFont(new Font("Georgia", Font.PLAIN, 14));
        indications.setBorder(new EmptyBorder(5,0,5,0));
        infos.add(indications);
        topCenterPanel.add(infos);

        this.size = (topPanel.getPreferredSize().width)/col;

        numCol.setLayout(new GridBagLayout());
        numCol.setBackground(Color.WHITE);
        numCol.setBorder(new MatteBorder(2, 0, 0, 0, Color.BLACK));

        for(int j = 0; j<col; j++){
            DrawNumber num = new DrawNumber(size, ""+(j+1));
            numCol.add(num);
        }

        topCenterPanel.add(numCol);

        centerPanel.add(topCenterPanel, BorderLayout.NORTH);

                //Dessin du panel grille
        grille.setLayout(null);

                    //Dessin des cases
        for(int i = 0; i<this.ligne; i++){
            for(int j = 0; j<this.col; j++){
                int pos = j;
                tableau[i][j] = new DrawCase(size, plateau[i][j], plateauWin[i][j]);
                tableau[i][j].setBounds(j*size, i*size, size, size);
                tableau[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent ev){
                        if(clic) control.getColChoix(pos);
                    }
                });
                grille.add(tableau[i][j]);
            }
        }
        grille.setPreferredSize(new Dimension(size*col,size*ligne));

        centerPanel.add(grille, BorderLayout.CENTER);

                //Dessin du panel des scores
        printScore.setLayout(new GridBagLayout());
        printScore.setBackground(Color.WHITE);
        printScore.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

        for(int j = 0; j<col; j++){
            DrawNumber colScore = new DrawNumber(size, "");
            printScore.add(colScore);
        }
        printScore.setVisible(false);

        centerPanel.add(printScore, BorderLayout.SOUTH);
        rightPanel.add(centerPanel, BorderLayout.CENTER);
        main.add(rightPanel, BorderLayout.EAST);

        //Dessin de leftPanel
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(new EmptyBorder(0,10,0,10));
        leftPanel.add(Box.createVerticalGlue());

            //Dessin du panel des scores
        affichScore.setLayout(new BoxLayout(affichScore, BoxLayout.Y_AXIS));
        affichScore.setBorder(new EmptyBorder(40, 5, 40, 5));
        affichScore.setOpaque(false);

        score = new JButton("Scores");
        score.setFont(new Font("Georgia", Font.PLAIN, 14));
        score.setAlignmentX(Component.CENTER_ALIGNMENT);
        affichScore.add(score);
        leftPanel.add(affichScore);

                //Dessin du premier séparateur de leftPanel
        sepLeft1.setMinimumSize(new Dimension(2,1));
        sepLeft1.setMaximumSize(new Dimension(leftPanel.getPreferredSize().width, 1));
        sepLeft1.setBackground(Color.GRAY);
        leftPanel.add(sepLeft1);

            //Dessin du panel menu
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(40, 5, 40, 5));
        menu.setOpaque(false);

                //Bouton cvc
        cvc = new JButton("0 joueur random");
        cvc.setFont(new Font("Georgia", Font.PLAIN, 14));
        cvc.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Bouton iavia
        iavia = new JButton("0 joueur ia");
        iavia.setFont(new Font("Georgia", Font.PLAIN, 14));
        iavia.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Bouton iaviabdd
        iaviabdd = new JButton("0 joueur ia bdd");
        iaviabdd.setFont(new Font("Georgia", Font.PLAIN, 14));
        iaviabdd.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Bouton cvp
        cvp = new JButton("1 joueur random");
        cvp.setFont(new Font("Georgia", Font.PLAIN, 14));
        cvp.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Bouton iavp
        iavp = new JButton("1 joueur ia");
        iavp.setFont(new Font("Georgia", Font.PLAIN, 14));
        iavp.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Bouton iavpbdd
        iavpbdd = new JButton("1 joueur ia bdd");
        iavpbdd.setFont(new Font("Georgia", Font.PLAIN, 14));
        iavpbdd.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Bouton pvp
        pvp = new JButton("2 joueurs");
        pvp.setFont(new Font("Georgia", Font.PLAIN, 14));
        pvp.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Mise en place des tailles des boutons de menu
        cvc.setMaximumSize(iaviabdd.getPreferredSize());
        iavia.setMaximumSize(iaviabdd.getPreferredSize());
        cvp.setMaximumSize(iaviabdd.getPreferredSize());
        iavp.setMaximumSize(iaviabdd.getPreferredSize());
        iavpbdd.setMaximumSize(iaviabdd.getPreferredSize());
        pvp.setMaximumSize(iaviabdd.getPreferredSize());

                //Ajout des boutons dans menu
        menu.add(Box.createVerticalGlue());
        menu.add(cvc);
        menu.add(Box.createVerticalStrut(10));
        menu.add(iavia);
        menu.add(Box.createVerticalStrut(10));
        menu.add(iaviabdd);
        menu.add(Box.createVerticalStrut(10));
        menu.add(cvp);
        menu.add(Box.createVerticalStrut(10));
        menu.add(iavp);
        menu.add(Box.createVerticalStrut(10));
        menu.add(iavpbdd);
        menu.add(Box.createVerticalStrut(10));
        menu.add(pvp);
        menu.add(Box.createVerticalGlue());
        menu.setMaximumSize(menu.getPreferredSize());
        leftPanel.add(menu);

                //Dessin du second séparateur de leftPanel
        sepLeft2.setMinimumSize(new Dimension(2,1));
        sepLeft2.setBackground(Color.GRAY);
        leftPanel.add(sepLeft2);

            //Dessin du panel param
        param.setLayout(new BoxLayout(param, BoxLayout.Y_AXIS));
        param.setBorder(new EmptyBorder(40, 5, 40, 5));
        param.setOpaque(false);

                //Bouton parametres
        parametres = new JButton("Paramètres");
        parametres.setFont(new Font("Georgia", Font.PLAIN, 14));
        parametres.setAlignmentX(Component.CENTER_ALIGNMENT);
        
                //Ajout du bouton dans param
        param.add(Box.createVerticalGlue());
        param.add(parametres);
        param.add(Box.createVerticalGlue());
        param.setMaximumSize(param.getPreferredSize());
        leftPanel.add(param);

                //Dessin du second séparateur de leftPanel
        sepLeft3.setMinimumSize(new Dimension(2,1));
        sepLeft3.setBackground(Color.GRAY);
        leftPanel.add(sepLeft3);

            //Dessin du panel saves
        saves.setLayout(new BoxLayout(saves, BoxLayout.Y_AXIS));
        saves.setBorder(new EmptyBorder(40, 5, 40, 5));
        saves.setOpaque(false);

                //Bouton save
        save = new JButton("Save");
        save.setFont(new Font("Georgia", Font.PLAIN, 14));
        save.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Bouton open
        open = new JButton("Open ex");
        open.setFont(new Font("Georgia", Font.PLAIN, 14));
        open.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Bouton openBdd
        openBdd = new JButton("Open in");
        openBdd.setFont(new Font("Georgia", Font.PLAIN, 14));
        openBdd.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Bouton review
        review = new JButton("Review");
        review.setFont(new Font("Georgia", Font.PLAIN, 14));
        review.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Bouton outilsBdd
        outilsBdd = new JButton("Outils BDD");
        outilsBdd.setFont(new Font("Georgia", Font.PLAIN, 14));
        outilsBdd.setAlignmentX(Component.CENTER_ALIGNMENT);

        save.setMaximumSize(outilsBdd.getPreferredSize());
        openBdd.setMaximumSize(outilsBdd.getPreferredSize());
        review.setMaximumSize(outilsBdd.getPreferredSize());
        open.setMaximumSize(outilsBdd.getPreferredSize());

                //Ajout des boutons dans saves
        saves.add(Box.createVerticalGlue());
        saves.add(save);
        saves.add(Box.createVerticalStrut(10));
        saves.add(open);
        saves.add(Box.createVerticalStrut(10));
        saves.add(openBdd);
        saves.add(Box.createVerticalStrut(10));
        saves.add(review);
        saves.add(Box.createVerticalStrut(10));
        saves.add(outilsBdd);
        saves.add(Box.createVerticalGlue());
        leftPanel.add(saves);
        leftPanel.add(Box.createVerticalGlue());

        main.add(leftPanel, BorderLayout.WEST);

        this.setContentPane(main);
        this.setResizable(false);
    }

    private String colorTurn(){
        switch(this.player){
            case 1 : 
                return "rouge";
            case 2 : 
                return "jaune";
            default : 
                return "pas de tour";
        }
    }

    public void updateGrille(int col, int ligne, int[][]plateau, boolean[][] plateauWin){
        this.ligne = ligne;
        this.col = col;
        this.plateau = plateau;
        this.plateauWin = plateauWin;

        for(int i = 0; i<this.ligne; i++){
            for(int j = 0; j<this.col; j++){
                tableau[i][j].modifPlayer(this.plateau[i][j], this.plateauWin[i][j]);
            }
        }
    }

    public void updateIndications(int turn){
        String indic;

        switch(turn){
            case 0 : 
                indic = "Votre couleur : rouge ";
                break;
            case 1 : 
                indic = "Votre couleur : jaune ";
                break;
            default : 
                indic = "";
                break;
        }

        indications.setText(indic);
        indications.revalidate();
        indications.repaint();
    }

    public void updateTurn(int player){
        this.player = player;

        turn.setText(" Tour : "+this.colorTurn());
        turn.revalidate();
        turn.repaint();
    }

    public void updateConfig(int player, int ligne, int col, int[][] plateau, boolean[][] plateauWin){
        this.player = player;
        this.ligne = ligne;
        this.col = col;
        this.plateau = plateau;
        
        this.size = (topPanel.getPreferredSize().width)/col;

        turn = new JLabel(" Tour : "+this.colorTurn());
        turn.revalidate();
        turn.repaint();

        numCol.removeAll();
        for(int j = 0; j<col; j++){
            DrawNumber num = new DrawNumber(size, ""+(j+1));
            numCol.add(num);
        }
        numCol.revalidate();
        numCol.repaint();

        grille.removeAll();

        for(int i = 0; i<this.ligne; i++){
            for(int j = 0; j<this.col; j++){
                int pos = j;
                tableau[i][j] = new DrawCase(size, plateau[i][j], plateauWin[i][j]);
                tableau[i][j].setBounds(j*size, i*size, size, size);
                tableau[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent ev){
                        if(clic) control.getColChoix(pos);
                    }
                });
                grille.add(tableau[i][j]);
            }
        }
        grille.setPreferredSize(new Dimension(size*col,size*ligne));
        grille.revalidate();
        grille.repaint();
        this.pack();
    }

    public void updateScore(int[] tabScore){
        printScore.removeAll();

        for(int j = 0; j<col; j++){
            DrawNumber colScore = new DrawNumber(size, ""+tabScore[j]);
            printScore.add(colScore);
        }

        printScore.revalidate();
        printScore.repaint();
    }

    public void updateModeJeu(int mode){
        String modePlay;
        switch (mode) {
            case 0 :
                modePlay = "IA VS IA Aléatoire ";
                break;
        
            case 1 : 
                modePlay = "IA VS IA ";
                break;
        
            case 2 : 
                modePlay = "IA VS Joueur Aléatoire ";
                break;
        
            case 3 : 
                modePlay = "IA VS Joueur ";
                break;
        
            case 4 : 
                modePlay = "Joueur VS Joueur ";
                break;

            default :
                modePlay = "Choisissez votre mode de jeu ";
                break;
        }

        modeJeu.setText(modePlay);
        modeJeu.revalidate();
        modeJeu.repaint();
    }

    public void setClic(boolean etat){
        clic = etat;
        for(int i = 0; i<ligne; i++){
            for(int j = 0; j<col; j++){
                tableau[i][j].setEnabled(etat);
            }
        }
    }

    public void setScoreVisibles(){
        if(scoreVisibles) scoreVisibles = false;
        else scoreVisibles = true;
        printScore.setVisible(scoreVisibles);
        centerPanel.revalidate();
        centerPanel.repaint();
        this.pack();
    }

    public void initScoreVisibles(){
        scoreVisibles = false;
        printScore.setVisible(scoreVisibles);
    }

    public JButton getCvc(){
        return this.cvc;
    }

    public JButton getIavia(){
        return this.iavia;
    }

    public JButton getIaviaBdd(){
        return this.iaviabdd;
    }

    public JButton getCvp(){
        return this.cvp;
    }

    public JButton getIavp(){
        return this.iavp;
    }

    public JButton getIavpBdd(){
        return this.iavpbdd;
    }

    public JButton getPvp(){
        return this.pvp;
    }

    public JButton getParametres(){
        return this.parametres;
    }

    public JButton getBack(){
        return this.back;
    }

    public JButton getForward(){
        return this.forward;
    }

    public JButton getSave(){
        return this.save;
    }

    public JButton getOpen(){
        return this.open;
    }

    public JButton getOpenBdd(){
        return this.openBdd;
    }

    public JButton getRestart(){
        return this.restart;
    }

    public JButton getStop(){
        return this.stop;
    }

    public JButton getPlay(){
        return this.play;
    }

    public JButton getPause(){
        return this.pause;
    }

    public JFileChooser getOpenFileSave(){
        return this.openFileSave;
    }

    public int getProf(){
        return (Integer) prof.getSelectedItem();
    }

    public JButton getSetProf(){
        return this.setProf;
    }

    public JButton getScore(){
        return this.score;
    }
    
    public boolean getScoreVisibles(){
        return this.scoreVisibles;
    }

    public JButton getOutilsBdd(){
        return this.outilsBdd;
    }
}