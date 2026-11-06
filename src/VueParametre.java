import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.awt.*;

public class VueParametre extends JFrame {
    private JPanel main = new JPanel();
    private JPanel title = new JPanel();
    private JPanel buttons = new JPanel();

    private JPanel panelComboBox = new JPanel();
    private JPanel buttonCombo = new JPanel();

    private JPanel panelTransitionColor = new JPanel();
    private JPanel panelTransitionLigne = new JPanel();
    private JPanel panelTransitionCol = new JPanel();

    private JLabel parametre;

    private JButton apply;
    private JButton quit;

    private JLabel configColor;
    private JLabel configLigne;
    private JLabel configCol;

    private JComboBox<String> choixColor;
    private JComboBox<Integer> choixLigne;
    private JComboBox<Integer> choixCol;

    private Configuration configuration;

    public VueParametre(Configuration config){
        this.configuration = config;

        main.setLayout(new BorderLayout());
        main.setBorder(new MatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));

        title.setLayout(new BoxLayout(title, BoxLayout.X_AXIS));
        title.setBackground(Color.LIGHT_GRAY);
        title.setBorder(new EmptyBorder(5,0,5,5));

        parametre = new JLabel(" Paramètres");
        parametre.setFont(new Font("Georgia", Font.BOLD, 20));
        title.add(parametre);

        main.add(title, BorderLayout.NORTH);

        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.setBackground(Color.WHITE);
        buttons.setBorder(new EmptyBorder(50, 80, 50, 80));

        buttonCombo.setLayout(new BoxLayout(buttonCombo, BoxLayout.X_AXIS));
        buttonCombo.setBackground(Color.WHITE);

        panelComboBox.setLayout(new BoxLayout(panelComboBox, BoxLayout.Y_AXIS));
        panelComboBox.setBackground(Color.WHITE);

        panelTransitionColor.setLayout(new BoxLayout(panelTransitionColor, BoxLayout.Y_AXIS));
        panelTransitionColor.setBackground(Color.WHITE);
        panelTransitionColor.setBorder(new EmptyBorder(10,10,10,10));

        configColor = new JLabel("Changez la première couleur à jouer");
        configColor.setFont(new Font("Georgia", Font.PLAIN, 14));
        configColor.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTransitionColor.add(configColor);

        choixColor = new JComboBox<>(this.configuration.getListeFirstPlayer());
        choixColor.setFont(new Font("Georgia", Font.PLAIN, 14));
		choixColor.setBackground(new Color(245,245,245));
		choixColor.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if(isSelected){
                    comp.setBackground(Color.WHITE);
                    comp.setForeground(Color.BLACK);
                } 

				else {
                    comp.setBackground(Color.WHITE);
                    comp.setForeground(Color.BLACK);
                }
                return comp;
            }
        });
		choixColor.setEditable(true);
		choixColor.getEditor().getEditorComponent().setBackground(new Color(241,250,247));
        panelTransitionColor.add(choixColor);

        panelTransitionCol.setLayout(new BoxLayout(panelTransitionCol, BoxLayout.Y_AXIS));
        panelTransitionCol.setBackground(Color.WHITE);
        panelTransitionCol.setBorder(new EmptyBorder(10,10,10,10));

        configCol = new JLabel("Changez la taille en colonne");
        configCol.setFont(new Font("Georgia", Font.PLAIN, 14));
        configCol.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTransitionCol.add(configCol);

        choixCol = new JComboBox<>(this.configuration.getListeCol());
        choixCol.setFont(new Font("Georgia", Font.PLAIN, 14));
		choixCol.setBackground(new Color(245,245,245));
		choixCol.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if(isSelected){
                    comp.setBackground(Color.WHITE);
                    comp.setForeground(Color.BLACK);
                } 

				else {
                    comp.setBackground(Color.WHITE);
                    comp.setForeground(Color.BLACK);
                }
                return comp;
            }
        });
		choixCol.setEditable(true);
		choixCol.getEditor().getEditorComponent().setBackground(new Color(241,250,247));
        panelTransitionCol.add(choixCol);

        panelTransitionLigne.setLayout(new BoxLayout(panelTransitionLigne, BoxLayout.Y_AXIS));
        panelTransitionLigne.setBackground(Color.WHITE);
        panelTransitionLigne.setBorder(new EmptyBorder(10,10,10,10));

        configLigne = new JLabel("Changez la taille en ligne");
        configLigne.setFont(new Font("Georgia", Font.PLAIN, 14));
        configLigne.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTransitionLigne.add(configLigne);

        choixLigne = new JComboBox<>(this.configuration.getListeLigne());
        choixLigne.setFont(new Font("Georgia", Font.PLAIN, 14));
		choixLigne.setBackground(new Color(245,245,245));
		choixLigne.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if(isSelected){
                    comp.setBackground(Color.WHITE);
                    comp.setForeground(Color.BLACK);
                } 

				else {
                    comp.setBackground(Color.WHITE);
                    comp.setForeground(Color.BLACK);
                }
                return comp;
            }
        });
		choixLigne.setEditable(true);
		choixLigne.getEditor().getEditorComponent().setBackground(new Color(241,250,247));
        panelTransitionLigne.add(choixLigne);

        panelComboBox.add(Box.createVerticalGlue());
        panelComboBox.add(panelTransitionColor);
        buttons.add(Box.createVerticalStrut(10));
        panelComboBox.add(panelTransitionCol);
        buttons.add(Box.createVerticalStrut(10));
        panelComboBox.add(panelTransitionLigne);
        panelComboBox.add(Box.createVerticalGlue());

        apply = new JButton("Apply");
        apply.setFont(new Font("Georgia", Font.PLAIN, 14));

        buttonCombo.add(Box.createVerticalGlue());
        buttonCombo.add(panelComboBox);
        buttonCombo.add(Box.createHorizontalStrut(20));
        buttonCombo.add(apply);
        buttonCombo.add(Box.createVerticalGlue());

        buttons.add(Box.createVerticalGlue());
        buttons.add(buttonCombo);

        quit = new JButton("Quitter");
        quit.setFont(new Font("Georgia", Font.PLAIN, 14));
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(Box.createVerticalStrut(40));
        buttons.add(quit);
        buttons.add(Box.createVerticalGlue());

        main.add(buttons, BorderLayout.CENTER);

        this.setContentPane(main);
    }

    public JButton getApply(){
        return this.apply;
    }

    public JButton getQuit(){
        return this.quit;
    }

    public int getColor(){
        switch((String) choixColor.getSelectedItem()){
            case "rouge" : return 1;
            default : return 2;
        }
    }

    public int getCol(){
        return (Integer) choixCol.getSelectedItem();
    }

    public int getLigne(){
        return (Integer) choixLigne.getSelectedItem();
    }
}