import java.awt.*;
import javax.swing.JPanel;

public class DrawCase extends JPanel {
    private int taille;
    private int player;
    private boolean win;

    public DrawCase(int taille, int player, boolean win){
        setBackground(Color.BLACK);
        setOpaque(true);
        this.taille = taille;
        this.player = player;
        this.win = win;
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(taille,taille);
    }

    @Override
    public Dimension getMinimumSize(){
        return new Dimension(taille,taille);
    }

    @Override
    public Dimension getMaximumSize(){
        return new Dimension(taille,taille);
    }

    @Override
    protected void paintComponent(Graphics graph){
        super.paintComponent(graph);

        
        int d = (int)(getWidth()* 0.8);
        int x = (getWidth() - d)/2;
        
        switch(player){
            case 1 :
                if(win == true) graph.setColor(Color.BLUE);
                else graph.setColor(Color.RED);
                break;
            case 2 : 
            if(win == true) graph.setColor(Color.GREEN);
                else graph.setColor(Color.YELLOW);
                break;
            default :
                graph.setColor(Color.WHITE);
                break;
        }
        graph.fillOval(x, x, d, d);
    }

    public void modifTaille(int newTaille){
        this.taille = newTaille;
        this.revalidate();
        this.repaint();
    }

    public void modifPlayer(int player, boolean win){
        this.player = player;
        this.win = win;
        this.revalidate();
        this.repaint();
    }
}