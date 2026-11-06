import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class VueWinner extends JFrame{
    private JPanel main = new JPanel();
    private JPanel group = new JPanel();
    private JPanel button = new JPanel();

    private JLabel victoryMessage;
    private JButton next;

    private int winner;
    private String colorWinner;

    public VueWinner(int winner){
        this.winner = winner;
        switch(this.winner){
            case 1 : 
                this.colorWinner = "Le joueur rouge a gagné !";
                break;
            case 2 : 
                this.colorWinner = "Le joueur jaune a gagné !";
                break;
            default : 
                this.colorWinner = "Match null";
                break;
        }

        main.setLayout(new BorderLayout());
        main.setBackground(Color.WHITE);
        main.setBorder(new MatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));

        group.setLayout(new BorderLayout());
        group.setBackground(Color.WHITE);
        group.setBorder(new EmptyBorder(10,10,10,10));

        victoryMessage = new JLabel(""+colorWinner);
        victoryMessage.setFont(new Font("Georgia", Font.PLAIN, 30));
        victoryMessage.setBackground(Color.WHITE);
        victoryMessage.setBorder(new EmptyBorder(10,10,10,10));

        group.add(victoryMessage, BorderLayout.NORTH);

        button.setLayout(new FlowLayout());
        button.setBackground(Color.WHITE);

        next = new JButton("Next");
        next.setFont(new Font("Georgia", Font.PLAIN, 20));
        button.add(next, BorderLayout.CENTER);
        group.add(button, BorderLayout.CENTER);

        main.add(group, BorderLayout.CENTER);

        this.setContentPane(main);
    }

    public void updateWinner(int winner){
        this.winner = winner;
        switch(winner){
            case 1 : 
                this.colorWinner = "Le joueur rouge a gagné !";
                break;
            case 2 : 
                this.colorWinner = "Le joueur jaune a gagné !";
                break;
            default : 
                this.colorWinner = "Match null";
                break;
        }
        victoryMessage.setText(""+colorWinner);
        victoryMessage.revalidate();
        victoryMessage.repaint();
    }

    public JButton getNext(){
        return this.next;
    }
}