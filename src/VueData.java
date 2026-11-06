import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.List;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.google.gson.Gson;

public class VueData extends JFrame{
    private int size;
    private ControllerSauvegarde control;

    private JPanel main = new JPanel();
    private JPanel listeSave = new JPanel();

    private JScrollPane scrollList;

    private DrawLine titreIndex;
    private DrawLine titreFirstPlayer;
    private DrawLine titreLigne;
    private DrawLine titreCol;
    private DrawLine titreGrille;
    private DrawLine titreCoups;

    private JPanel boutons = new JPanel();

    private JButton ouvrir;
    private JButton annuler;

    private Gson gson = new Gson();

    public VueData(){
        this.setTitle("Sélectionner une sauvegarde");

        main.setLayout(new BorderLayout());
        main.setBackground(Color.WHITE);
        main.setPreferredSize(new Dimension(1000, 200));

        size = ((main.getPreferredSize().width)/6)-10;
        listeSave.setLayout(new GridLayout(0, 6));
        listeSave.setBackground(Color.WHITE);

        titreIndex = new DrawLine(size, "Index partie");
        listeSave.add(titreIndex);

        titreFirstPlayer = new DrawLine(size, "Premier joueur");
        listeSave.add(titreFirstPlayer);

        titreLigne = new DrawLine(size, "Taille lignes");
        listeSave.add(titreLigne);

        titreCol = new DrawLine(size, "Taille colonnes");
        listeSave.add(titreCol);

        titreGrille = new DrawLine(size, "Grille");
        listeSave.add(titreGrille);

        titreCoups = new DrawLine(size, "Coups");
        listeSave.add(titreCoups);

        scrollList = new JScrollPane(listeSave);
        scrollList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        main.add(scrollList, BorderLayout.NORTH);

        boutons.setLayout(new BoxLayout(boutons, BoxLayout.X_AXIS));
        boutons.setBackground(new Color(222, 248, 249));
        boutons.setBorder(new EmptyBorder(10, 10, 10, 10));

        ouvrir = new JButton("Ouvrir");
        ouvrir.setFont(new Font("Georgia", Font.PLAIN, 16));

        annuler = new JButton("Annuler");
        annuler.setFont(new Font("Georgia", Font.PLAIN, 16));

        boutons.add(Box.createHorizontalGlue());
        boutons.add(ouvrir);
        boutons.add(Box.createHorizontalStrut(10));
        boutons.add(annuler);

        main.add(boutons, BorderLayout.SOUTH);

        this.setContentPane(main);
    }

    public void updateAllSaves(List<Sauvegarde> allSaves){
        listeSave.removeAll();

        listeSave.add(titreIndex);
        listeSave.add(titreFirstPlayer);
        listeSave.add(titreLigne);
        listeSave.add(titreCol);
        listeSave.add(titreGrille);
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
            listeSave.add(coups);
        }

        listeSave.revalidate();
        listeSave.repaint();
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
}