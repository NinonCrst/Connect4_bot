import java.awt.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class DrawNumber extends JPanel{
    private int taille;
    private JLabel label;

    public DrawNumber(int taille, String number){
        this.taille = taille;

        setBackground(Color.WHITE);
        setOpaque(true);
        setLayout(new BorderLayout());
        setBorder(new MatteBorder(0, 1, 0, 1, Color.BLACK));

        label = new JLabel(number);
        label.setFont(new Font("Georgia", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        add(label, BorderLayout.CENTER);
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(taille,20);
    }

    @Override
    public Dimension getMinimumSize(){
        return new Dimension(taille,20);
    }

    @Override
    public Dimension getMaximumSize(){
        return new Dimension(taille,20);
    }

    public void setTest(String txt){
        label.setText(txt);
    }

    public String getText(){
        return label.getText();
    }
}