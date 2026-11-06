import java.io.*;

public class Configuration {
    private int firstPlayer;
    private int ligne;
    private int col;
    private int index;

    private final String[] listeFirstPlayer = {"rouge", "jaune"};
    private final Integer[] listeLigne = {6, 7, 8, 9, 10};
    private final Integer[] listeCol = {7, 8, 9, 10};

    public Configuration(String path){
        load(path);
    }

    public void load(String path){
        try (BufferedReader read = new BufferedReader(new FileReader(path))){
            this.firstPlayer = Integer.parseInt(read.readLine());
            this.ligne = Integer.parseInt(read.readLine());
            this.col = Integer.parseInt(read.readLine());
            this.index = Integer.parseInt(read.readLine());
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public void save(String path){
        try(PrintWriter write = new PrintWriter(new FileWriter(path))){
            write.println(this.firstPlayer);
            write.println(this.ligne);
            write.println(this.col);
            write.println(this.index);
        } catch(IOException exc){
            exc.printStackTrace();
        }
    }

    public int getFirstPlayer(){
        return this.firstPlayer;
    }

    public int getLigne(){
        return this.ligne;
    }

    public int getCol(){
        return this.col;
    }

    public int getIndex(){
        return this.index;
    }

    public void setFirstPlayer(int player){
        this.firstPlayer = player;
    }

    public void setLigne(int ligne){
        this.ligne = ligne;
    }

    public void setCol(int col){
        this.col = col;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public String[] getListeFirstPlayer(){
        return this.listeFirstPlayer;
    }

    public Integer[] getListeLigne(){
        return this.listeLigne;
    }

    public Integer[] getListeCol(){
        return this.listeCol;
    }
}