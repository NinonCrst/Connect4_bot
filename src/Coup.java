public class Coup {
    private int ligne;
    private int col;
    private int player;

    public Coup(int ligne, int col, int player){
        this.ligne = ligne;
        this.col = col;
        this.player = player;
    }

    public int getLigneCoup(){
        return this.ligne;
    }

    public int getColCoup(){
        return this.col;
    }

    public int getPlayerCoup(){
        return this.player;
    }
}