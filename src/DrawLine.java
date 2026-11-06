import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class DrawLine extends JPanel {
    private int taille;
    private JLabel label;

    public DrawLine(int taille, String text){
        this.taille = taille;

        setBackground(Color.WHITE);
        setOpaque(true);
        setLayout(new BorderLayout());
        setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));

        label = new JLabel(text);
        label.setFont(new Font("Georgia", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(5, 5, 5,5));

        add(label, BorderLayout.CENTER);
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(taille,28);
    }

    @Override
    public Dimension getMinimumSize(){
        return new Dimension(taille,28);
    }

    @Override
    public Dimension getMaximumSize(){
        return new Dimension(taille,28);
    }

    public void setText(String txt){
        label.setText(txt);
    }

    public String getText(){
        return label.getText();
    }
}