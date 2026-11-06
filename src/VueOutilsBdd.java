import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.util.List;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.google.gson.Gson;

public class VueOutilsBdd extends JFrame {
    private int size;
    private ControllerSauvegarde control;

    private ModelConnect4 partieCano;
    private ModelConnect4 partieSym;

    private JPanel main = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    //panel haut du rightPanel
    private JPanel topPanel = new JPanel();
    //panel bas du rightPanel
    private JPanel bottomPanel = new JPanel();

    private JPanel listeSave = new JPanel();

    private JScrollPane scrollList;

    private DrawLine titreIndex;
    private DrawLine titreFirstPlayer;
    private DrawLine titreLigne;
    private DrawLine titreCol;
    private DrawLine titreGrille;
    private DrawLine titreSym;
    private DrawLine titreCoups;

    private JPanel boutons = new JPanel();

    private JButton ouvrir;
    private JButton annuler;

    private JPanel infosPanel = new JPanel();

    private JLabel titreColGrille = new JLabel("Canonique");
    private JLabel titreColSym = new JLabel("Symétrie");

    private JPanel grillesPanel = new JPanel();
    private JPanel rightGrille = new JPanel();
    private JPanel leftSym = new JPanel();

    private DrawCase[][] canonique;
    private DrawCase[][] symetrie;

    private JButton back;
    private JButton forward;

    private Gson gson = new Gson();

    public VueOutilsBdd(){
        this.setTitle("Outils Base de données");
        canonique = new DrawCase[8][9];
        symetrie = new DrawCase[8][9];
        partieCano = new ModelConnect4();
        partieSym = new ModelConnect4();

        main.setLayout(new BorderLayout());
        main.setBackground(Color.WHITE);

        rightPanel.setLayout(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new MatteBorder(0, 2, 0, 0, Color.BLACK));

        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);

        listeSave.setLayout(new GridLayout(0, 7));
        listeSave.setBackground(Color.WHITE);

        titreIndex = new DrawLine(200, "Index partie");
        listeSave.add(titreIndex);

        titreFirstPlayer = new DrawLine(200, "Premier joueur");
        listeSave.add(titreFirstPlayer);

        titreLigne = new DrawLine(200, "Taille lignes");
        listeSave.add(titreLigne);

        titreCol = new DrawLine(200, "Taille colonnes");
        listeSave.add(titreCol);

        titreGrille = new DrawLine(200, "Grille");
        listeSave.add(titreGrille);

        titreSym = new DrawLine(200, "Symétrie");
        listeSave.add(titreSym);

        titreCoups = new DrawLine(200, "Coups");
        listeSave.add(titreCoups);

        scrollList = new JScrollPane(listeSave);
        scrollList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        topPanel.add(scrollList, BorderLayout.CENTER);

        boutons.setLayout(new BoxLayout(boutons, BoxLayout.Y_AXIS));
        boutons.setBackground(new Color(222, 248, 249));
        boutons.setBorder(new EmptyBorder(10, 10, 10, 10));

        ouvrir = new JButton("Ouvrir");
        ouvrir.setFont(new Font("Georgia", Font.PLAIN, 16));

        annuler = new JButton("Annuler");
        annuler.setFont(new Font("Georgia", Font.PLAIN, 16));
        ouvrir.setMaximumSize(annuler.getPreferredSize());

        boutons.add(Box.createVerticalGlue());
        boutons.add(ouvrir);
        boutons.add(Box.createVerticalStrut(10));
        boutons.add(annuler);
        boutons.add(Box.createVerticalGlue());

        topPanel.add(boutons, BorderLayout.EAST);
        rightPanel.add(topPanel, BorderLayout.NORTH);

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setOpaque(false);

        infosPanel.setLayout(new GridLayout(1,2));
        infosPanel.setOpaque(false);

        titreColGrille.setFont(new Font("Georgia", Font.PLAIN, 14));
        titreColGrille.setHorizontalAlignment(SwingConstants.LEFT);
        titreColGrille.setVerticalAlignment(SwingConstants.CENTER);
        titreColGrille.setBorder(new EmptyBorder(5, 0, 5,0));
        infosPanel.add(titreColGrille);

        titreColSym.setFont(new Font("Georgia", Font.PLAIN, 14));
        titreColSym.setHorizontalAlignment(SwingConstants.LEFT);
        titreColSym.setVerticalAlignment(SwingConstants.CENTER);
        titreColSym.setBorder(new EmptyBorder(5, 0, 5,0));
        infosPanel.add(titreColSym);
        bottomPanel.add(infosPanel, BorderLayout.NORTH);

        grillesPanel.setLayout(new GridLayout(1,2));
        grillesPanel.setOpaque(false);

        this.size = (topPanel.getPreferredSize().width)/9/2;
        
        rightGrille.setLayout(null);
        rightGrille.setBorder(new EmptyBorder(0, 0, 0, 2));

        for(int i = 0; i<8; i++){
            for(int j = 0; j<9; j++){
                canonique[i][j] = new DrawCase(size, 0, false);
                canonique[i][j].setBounds(j*size, i*size, size, size);
                rightGrille.add(canonique[i][j]);
            }
        }
        rightGrille.setPreferredSize(new Dimension(size*9,size*8));
        grillesPanel.add(rightGrille);
        
        leftSym.setLayout(null);
        leftSym.setBorder(new EmptyBorder(0, 2, 0, 0));

        for(int i = 0; i<8; i++){
            for(int j = 0; j<9; j++){
                symetrie[i][j] = new DrawCase(size, 0, false);
                symetrie[i][j].setBounds(j*size, i*size, size, size);
                leftSym.add(symetrie[i][j]);
            }
        }
        leftSym.setPreferredSize(new Dimension(size*9,size*8));
        grillesPanel.add(leftSym);
        bottomPanel.add(grillesPanel, BorderLayout.CENTER);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        main.add(rightPanel, BorderLayout.EAST);

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        back = new JButton("Back");
        back.setFont(new Font("Georgia", Font.PLAIN, 14));

        forward = new JButton("Forward");
        forward.setFont(new Font("Georgia", Font.PLAIN, 14));
        back.setMaximumSize(forward.getPreferredSize());

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(back);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(forward);
        leftPanel.add(Box.createVerticalGlue());

        main.add(leftPanel, BorderLayout.WEST);

        this.setContentPane(main);
        this.setResizable(false);
    }

    public void updateAllSaves(List<Sauvegarde> allSaves){
        listeSave.removeAll();

        listeSave.add(titreIndex);
        listeSave.add(titreFirstPlayer);
        listeSave.add(titreLigne);
        listeSave.add(titreCol);
        listeSave.add(titreGrille);
        listeSave.add(titreSym);
        listeSave.add(titreCoups);

        for(Sauvegarde save : allSaves){
            DrawLine index = new DrawLine(size, ""+save.getIndexSave());
            index.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    control.setIndexChoosen(save.getIndexSave());
                }
            });
            
            DrawLine firstPlayer = new DrawLine(size, ""+save.getFirstPlayerSave());
            firstPlayer.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    control.setIndexChoosen(save.getIndexSave());
                }
            });
            
            DrawLine ligne = new DrawLine(size, ""+save.getLigneSave());
            ligne.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    control.setIndexChoosen(save.getIndexSave());
                }
            });
            
            DrawLine col = new DrawLine(size, ""+save.getColSave());
            col.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    control.setIndexChoosen(save.getIndexSave());
                }
            });

            String grilleAffiche = gson.toJson(save.getGrilleSave());
            
            DrawLine grille = new DrawLine(size, grilleAffiche);
            grille.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    control.setIndexChoosen(save.getIndexSave());
                }
            });

            String symAffiche = gson.toJson(save.getGrilleSave());
            
            DrawLine sym = new DrawLine(size, symAffiche);
            sym.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    control.setIndexChoosen(save.getIndexSave());
                }
            });

            String coupsAffiche = gson.toJson(save.getCoupsSave());
            
            DrawLine coups = new DrawLine(size, coupsAffiche);
            coups.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    control.setIndexChoosen(save.getIndexSave());
                }
            });

            listeSave.add(index);
            listeSave.add(firstPlayer);
            listeSave.add(ligne);
            listeSave.add(col);
            listeSave.add(grille);
            listeSave.add(sym);
            listeSave.add(coups);
        }

        listeSave.revalidate();
        listeSave.repaint();
    }

    public void updateGrillesAffichage(){
        this.size = (topPanel.getPreferredSize().width)/partieCano.getCol()/2;
        
        rightGrille.removeAll();
        rightGrille.setBorder(new EmptyBorder(0, 0, 0, 2));

        for(int i = 0; i<partieCano.getLigne(); i++){
            for(int j = 0; j<partieCano.getCol(); j++){
                canonique[i][j] = new DrawCase(size, partieCano.getGrille()[i][j], partieCano.getGrilleWin()[i][j]);
                canonique[i][j].setBounds(j*size, i*size, size, size);
                rightGrille.add(canonique[i][j]);
            }
        }
        rightGrille.setPreferredSize(new Dimension(size*partieCano.getCol(),size*partieCano.getLigne()));
        rightGrille.revalidate();
        rightGrille.repaint();

        leftSym.removeAll();
        leftSym.setBorder(new EmptyBorder(0, 2, 0, 0));

        for(int i = 0; i<partieSym.getLigne(); i++){
            for(int j = 0; j<partieSym.getCol(); j++){
                symetrie[i][j] = new DrawCase(size, partieSym.getGrille()[i][j], partieSym.getGrilleWin()[i][j]);
                symetrie[i][j].setBounds(j*size, i*size, size, size);
                leftSym.add(symetrie[i][j]);
            }
        }
        leftSym.setPreferredSize(new Dimension(size*partieSym.getCol(),size*partieSym.getLigne()));
        leftSym.revalidate();
        leftSym.repaint();
        this.pack();
    }

    public void setController(ControllerSauvegarde control){
        this.control = control;
    }

    public JButton getOuvrir(){
        return this.ouvrir;
    }

    public JButton getAnnuler(){
        return this.annuler;
    }

    public ModelConnect4 getPartieCano(){
        return this.partieCano;
    }

    public ModelConnect4 getPartieSym(){
        return this.partieSym;
    }

    public JButton getBack(){
        return this.back;
    }

    public JButton getForward(){
        return this.forward;
    }
}