import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Timer;

public class ModelConnect4 {
    private int[][] grille;
    private int ligne;
    private int col;
    private int player;
    private int index;

    private boolean[][]grilleWin;

    private int colChoix;
    private int currentTurn = -1; 
    private int currentModeP;
    private VueBoard currentVBoard;
    private VueWinner currentVWinner;
    private Timer tourIA;

    private boolean pauseGame = false;
    private boolean currentModeIA = false;

    private int currentModeG = -1;

    private List<Coup> coups;
    private List<Coup> coupsAnnules;

    private boolean inGame = false;

    private boolean statut = false;
    private int playerTurn = -1;

    private Timer tourIAMinMax;
    private int profondeur = 6;
    private int[] tabScore;

    private int confiance = 0;
    private Timer tourIAMinMaxBdd;

    public ModelConnect4(int firstPlayer, int ligne, int col, int profondeur){
        this.player = firstPlayer;
        this.ligne = ligne;
        this.col = col;
        this.profondeur = profondeur;

        this.grille = new int[this.ligne][this.col];
        this.grilleWin = new boolean[this.ligne][this.col];

        this.coups = new ArrayList<>();
        this.coupsAnnules = new ArrayList<>();

        this.initBoard();
    }

    public ModelConnect4(){}

    public void openSave(Sauvegarde save){
        this.player = save.getFirstPlayerSave();
        this.ligne = save.getLigneSave();
        this.col = save.getColSave();
        this.index = save.getIndexSave();
        
        this.currentTurn = save.getCurrentTurnSave();
        this.currentModeG = save.getModeSave();
        this.statut = save.getStatutSave();
        
        this.grille = copyGrille(save.getGrilleSave());
        this.grilleWin = copyGrilleWin(save.getGrilleWinSave());
        
        this.coups = copyCoups(save.getCoupsSave());
        this.coupsAnnules = copyCoups(save.getCoupsAnnulesSave());
    }

    public Sauvegarde setSave(){
        Sauvegarde save = new Sauvegarde(this);
        return save;
    }
    
    public void setIndex(int index){
        this.index = index;
    }

    public void initBoard(){
        for(int i = 0; i<ligne; i++){
            for(int j = 0; j<col; j++){
                this.grille[i][j] = 0;
                this.grilleWin[i][j] = false;
            }
        }
    }

    public void initWin(){
        for(int i = 0; i<ligne; i++){
            for(int j = 0; j<col; j++){
                this.grilleWin[i][j] = false;
            }
        }
    }

    public int[][] copyGrille(int[][] originalGrille){
        int[][] copy = new int[ligne][col];

        for(int i = 0; i<ligne; i++){
            for(int j = 0; j<col; j++){
                copy[i][j] = originalGrille[i][j];
            }
        }
        return copy;
    }

    public boolean[][] copyGrilleWin(boolean[][] originalGrilleWin){
        boolean[][] copy = new boolean[ligne][col];

        for(int i = 0; i<ligne; i++){
            for(int j = 0; j<col; j++){
                copy[i][j] = originalGrilleWin[i][j];
            }
        }
        return copy;
    }

    public List<Coup> copyCoups(List<Coup> originalCoups){
        List<Coup> copy = new ArrayList<>();

        for(Coup co : originalCoups){
            copy.add(co);
        }
        return copy;
    }

    public int[][] getGrille(){
        return this.grille;
    }

    public boolean[][] getGrilleWin(){
        return this.grilleWin;
    }

    public int getLigne(){
        return this.ligne;
    }

    public int getCol(){
        return this.col;
    }

    public int getPlayer(){
        return this.player;
    }

    public int getCurrentTurn(){
        return this.currentTurn;
    }

    public boolean getCurrentModeIA(){
        return this.currentModeIA;
    }

    public int getCurrentModeG(){
        return this.currentModeG;
    }

    public boolean getInGame(){
        return this.inGame;
    }

    public int getIndex(){
        return this.index;
    }

    public int getPlayerTurn(){
        return this.playerTurn;
    }

    public List<Coup> getCoups(){
        return this.coups;
    }

    public int getProfondeur(){
        return this.profondeur;
    }

    public int[] getTabScore(){
        return this.tabScore;
    }

    public int getConfiance(){
        return this.confiance;
    }

    public int victory(){
        int winner = 0;
        int count = 1;

        for(int i = 0; i<ligne; i++){
            for(int j = 0; j<(col-1); j++){
                if((grille[i][j] == grille[i][j+1])&&(grille[i][j] != 0)){
                    winner = grille[i][j];
                    count++;
                    grilleWin[i][j] = true;
                    if(j == (col-2)) grilleWin[i][j+1] = true;
                }

                else{
                    if(count < 4){
                        winner = 0;
                        count = 1;
                        this.initWin();
                    }
                    else{
                        grilleWin[i][j] = true;
                        return winner;
                    }
                }
            }
            if(count >= 4) return winner;
            winner = 0;
            count = 1;
            this.initWin();
        }

        winner = 0;
        count = 1;
        for(int j = 0; j<col; j++){
            for(int i = 0; i<(ligne-1); i++){
                if((grille[i][j] != 0)&&(grille[i][j] == grille[i+1][j])){
                    winner = grille[i][j];
                    count++;
                    grilleWin[i][j] = true;
                    if(i == (ligne-2)) grilleWin[i+1][j] = true;
                }

                else{
                    if(count < 4){
                        winner = 0;
                        count = 1;
                        this.initWin();
                    }
                    else{
                        grilleWin[i][j] = true;
                        return winner;
                    }
                }
            }
            if(count >= 4) return winner;
            winner = 0;
            count = 1;
            this.initWin();
        }

        winner = 0;
        count = 1;
        for(int j = 0; j<(col-3); j++){
            for(int l = 0, c = j; (l<(ligne-1)) && (c<(col-1)); l++, c++){
                if((grille[l][c] != 0)&&(grille[l][c] == grille[l+1][c+1])){
                    winner = grille[l][c];
                    count++;
                    grilleWin[l][c] = true;
                    if((l == (ligne-2))||(c == (col-2))) grilleWin[l+1][c+1] = true;
                }
                else{
                    if(count < 4){
                        winner = 0;
                        count = 1;
                        this.initWin();
                    }
                    else{
                        grilleWin[l][c] = true;
                        return winner;
                    }
                }
            }
            if(count >= 4) return winner;
            winner = 0;
            count = 1;
            this.initWin();
        }

        winner = 0;
        count = 1;
        for(int i = 0; i<(ligne-3); i++){
            for(int l = i, c = 0; (l<(ligne-1)) && (c<(col-1)); l++, c++){
                if((grille[l][c] != 0)&&(grille[l][c] == grille[l+1][c+1])){
                    winner = grille[l][c];
                    count++;
                    grilleWin[l][c] = true;
                    if((l == (ligne-2))||(c == (col-2))) grilleWin[l+1][c+1] = true;
                }
                else{
                    if(count < 4){
                        winner = 0;
                        count = 1;
                        this.initWin();
                    }
                    else{
                        grilleWin[l][c] = true;
                        return winner;
                    }
                }
            }
            if(count >= 4) return winner;
            winner = 0;
            count = 1;
            this.initWin();
        }

        winner = 0;
        count = 1;
        for(int j = (col-1); j>2; j--){
            for(int l = 0, c = j; (l<(ligne-1)) && (c>0); l++, c--){
                if((grille[l][c] != 0)&&(grille[l][c] == grille[l+1][c-1])){
                    winner = grille[l][c];
                    count++;
                    grilleWin[l][c] = true;
                    if((l == (ligne-2))||(c == 1)) grilleWin[l+1][c-1] = true;
                }
                else{
                    if(count < 4){
                        winner = 0;
                        count = 1;
                        this.initWin();
                    }
                    else{
                        grilleWin[l][c] = true;
                        return winner;
                    }
                }
            }
            if(count >= 4) return winner;
            winner = 0;
            count = 1;
            this.initWin();
        }

        winner = 0;
        count = 1;
        for(int i = 0; i<(ligne-3); i++){
            for(int l = i, c = (col-1); (l<(ligne-1)) && (c>0); l++, c--){
                if((grille[l][c] != 0)&&(grille[l][c] == grille[l+1][c-1])){
                    winner = grille[l][c];
                    count++;
                    grilleWin[l][c] = true;
                    if((l == (ligne-2))||(c == 1)) grilleWin[l+1][c-1] = true;
                }
                else{
                    if(count < 4){
                        winner = 0;
                        count = 1;
                        this.initWin();
                    }
                    else{
                        grilleWin[l][c] = true;
                        return winner;
                    }
                }
            }
            if(count >= 4) return winner;
            winner = 0;
            count = 1;
            this.initWin();
        }

        return winner;
    }

    public void setGrille(int[][] grille){
        this.grille = grille;
    }

    public void setColChoix(int choix){
        this.colChoix = choix;
        this.playGamePBlock(currentVBoard, currentVWinner, currentTurn, currentModeP);
    }

    public void setProfondeur(int profondeur){
        this.profondeur = profondeur;
    }

    public void setPause(){
        this.pauseGame = true;
        if(currentModeG == 0) tourIA.stop();
        else if(currentModeG == 1) tourIAMinMax.stop();
    }

    public void removePause(){
        this.pauseGame = false;
        if(currentModeG == 0) tourIA.start();
        else if(currentModeG == 1) tourIAMinMax.start();
    }

    public void setChanges(int firstPlayer, int ligne, int col){
        this.player = firstPlayer;
        this.ligne = ligne;
        this.col = col;

        this.grille = new int[ligne][col];
        this.grilleWin = new boolean[ligne][col];
        this.initBoard();
    }

    public void backCoup(){
        if(!coups.isEmpty()){
            Coup back = coups.removeLast();
            coupsAnnules.add(back);
            grille[back.getLigneCoup()][back.getColCoup()] = 0;
            this.victory();
            if(currentTurn == 1) currentTurn = 2;
            else currentTurn = 1;
        } 
    }

    public void forwardCoup(){
        if(!coupsAnnules.isEmpty()){
            Coup forward = coupsAnnules.removeLast();
            coups.add(forward);
            grille[forward.getLigneCoup()][forward.getColCoup()] = forward.getPlayerCoup();
            this.victory();
            if(forward.getPlayerCoup() == 1) currentTurn = 2;
            else currentTurn = 1;
        }
    }

    public int addJeton(int col, int player){
        for(int i = (ligne-1); i>=0; i--){
            if(grille[i][col] == 0){
                grille[i][col] = player;

                Coup move = new Coup(i, col, player);
                coups.add(move);
                coupsAnnules = new ArrayList<>();

                if(player == 1) return 2;
                else return 1;
            }
        }
        return -1;
    }

    public boolean matchNull(){
        for(int i = 0; i<ligne; i++){
            for(int j = 0; j<col; j++){
                if(grille[i][j] == 0) return false;
            }
        }
        return true;
    }

    public void afficherWinner(VueWinner vWinner, int winner){
        statut = true;

        vWinner.updateWinner(winner);
        vWinner.pack();
        vWinner.setVisible(true);

        inGame = false;
        
        if((tourIAMinMaxBdd != null)&&tourIAMinMaxBdd.isRunning()) tourIAMinMaxBdd.stop();
        if((tourIAMinMax != null)&&tourIAMinMax.isRunning()) tourIAMinMax.stop();
        if((tourIA != null)&&tourIA.isRunning()) tourIA.stop();
    }

    public int tourPlayer(int player){
        int nextTurn = addJeton(colChoix, player);
        while(nextTurn == -1) nextTurn = addJeton(colChoix, player);
        return nextTurn;
    }

    public int tourIARandom(int player){
        List<Integer> choixIA = new ArrayList<>();
        for(int j = 0; j<col; j++) choixIA.add(j);
        Collections.shuffle(choixIA);

        for(int j = 0; j<col; j++){
            int nextTurn = addJeton(choixIA.get(j), player);
            if(nextTurn != -1) return nextTurn;
        }
        return -1;
    }

    public int tourIAAlgoMinMax(int player, VueBoard vBoard){
        AlgoMiniMax miniMax = new AlgoMiniMax();
        int colMiniMax = miniMax.calculCoup(grille, profondeur, ligne, col, player);
        int nextTurn = addJeton(colMiniMax, player);
        tabScore = miniMax.getScoreTab();
        vBoard.updateScore(tabScore);
        return nextTurn;
    }

    public int tourIAAlgoMinMaxBdd(int player, VueBoard vBoard){
        AlgoMiniMax miniMax = new AlgoMiniMax();
        SauvegardeDatabase saveData = new SauvegardeDatabase();
        Coup coupBdd = saveData.openCoup(coups);
        int colChoose;

        if(coups != null) colChoose = coupBdd.getColCoup();
        else colChoose = miniMax.calculCoup(grille, profondeur, ligne, col, player);

        int nextTurn = addJeton(colChoose, player);
        tabScore = miniMax.getScoreTab();
        vBoard.updateScore(tabScore);
        return nextTurn;
    }

    public void playGamePBlock(VueBoard vBoard, VueWinner vWinner, int turn, int currentMode){
        int nextTurn = tourPlayer(turn);

        vBoard.updateGrille(col, ligne, grille, grilleWin);
        vBoard.updateTurn(nextTurn);
        vBoard.setClic(false);
        switch(currentMode){
            case 3 : 
                this.playGameC(vBoard, vWinner, nextTurn, currentMode);
                break;
            case 4 : 
                this.playGameIa(vBoard, vWinner, nextTurn, currentMode);
                break;
            case 5 :
                this.playGameIaBdd(vBoard, vWinner, nextTurn, currentMode);
                break;
            case 6 :
                this.playGameP(vBoard, vWinner, nextTurn, currentMode);
                break;
        }
    }

    public void playGameC(VueBoard vBoard, VueWinner vWinner, int turn, int currentMode){
        vBoard.setClic(false);
        int winner = victory();
        vBoard.updateGrille(col, ligne, grille, grilleWin);
        if(winner != 0){
            vBoard.updateModeJeu(-1);
            this.afficherWinner(vWinner, winner);
            return;
        }
        if(matchNull()){
            vBoard.updateModeJeu(-1);
            this.afficherWinner(vWinner, -1);
            return;
        }
        vBoard.setClic(false);
        if((tourIAMinMaxBdd != null)&&tourIAMinMaxBdd.isRunning()) tourIAMinMaxBdd.stop();
        if((tourIAMinMax != null)&&tourIAMinMax.isRunning()) tourIAMinMax.stop();
        if((tourIA != null)&&tourIA.isRunning()) tourIA.stop();
        tourIA = new Timer(500, e -> {
            int nextTurn = tourIARandom(turn);
            vBoard.updateGrille(col, ligne, grille, grilleWin);
            if(nextTurn != -1) vBoard.updateTurn(nextTurn);
            tourIA.stop();
            switch(currentMode){
                case 0 :
                    this.playGameC(vBoard, vWinner, nextTurn, currentMode);
                    break;
                case 3 : 
                    vBoard.setClic(true);
                    this.playGameP(vBoard, vWinner, nextTurn, currentMode);
                    break;
            }
        });
        tourIA.setRepeats(false);
        tourIA.start();
    }

    public void playGameIa(VueBoard vBoard, VueWinner vWinner, int turn, int currentMode){
        vBoard.setClic(false);
        int winner = victory();
        vBoard.updateGrille(col, ligne, grille, grilleWin);
        if(winner != 0){
            vBoard.updateModeJeu(-1);
            this.afficherWinner(vWinner, winner);
            return;
        }
        if(matchNull()){
            vBoard.updateModeJeu(-1);
            this.afficherWinner(vWinner, -1);
            return;
        }
        vBoard.setClic(false);
        if((tourIAMinMaxBdd != null)&&tourIAMinMaxBdd.isRunning()) tourIAMinMaxBdd.stop();
        if((tourIAMinMax != null)&&tourIAMinMax.isRunning()) tourIAMinMax.stop();
        if((tourIA != null)&&tourIA.isRunning()) tourIA.stop();
        tourIAMinMax = new Timer(500, e -> {
            int nextTurn = tourIAAlgoMinMax(turn, vBoard);
            vBoard.updateGrille(col, ligne, grille, grilleWin);
            if(nextTurn != -1) vBoard.updateTurn(nextTurn);
            tourIAMinMax.stop();
            switch(currentMode){
                case 1 :
                    this.playGameIa(vBoard, vWinner, nextTurn, currentMode);
                    break;
                case 4 : 
                    vBoard.setClic(true);
                    this.playGameP(vBoard, vWinner, nextTurn, currentMode);
                    break;
            }
        });
        tourIAMinMax.setRepeats(false);
        tourIAMinMax.start();
    }

    public void playGameIaBdd(VueBoard vBoard, VueWinner vWinner, int turn, int currentMode){
        vBoard.setClic(false);
        int winner = victory();
        vBoard.updateGrille(col, ligne, grille, grilleWin);
        if(winner != 0){
            vBoard.updateModeJeu(-1);
            this.afficherWinner(vWinner, winner);
            return;
        }
        if(matchNull()){
            vBoard.updateModeJeu(-1);
            this.afficherWinner(vWinner, -1);
            return;
        }
        vBoard.setClic(false);
        if((tourIAMinMaxBdd != null)&&tourIAMinMaxBdd.isRunning()) tourIAMinMaxBdd.stop();
        if((tourIAMinMax != null)&&tourIAMinMax.isRunning()) tourIAMinMax.stop();
        if((tourIA != null)&&tourIA.isRunning()) tourIA.stop();
        tourIAMinMaxBdd = new Timer(500, e -> {
            int nextTurn = tourIAAlgoMinMaxBdd(turn, vBoard);
            vBoard.updateGrille(col, ligne, grille, grilleWin);
            if(nextTurn != -1) vBoard.updateTurn(nextTurn);
            tourIAMinMaxBdd.stop();
            switch(currentMode){
                case 2 :
                    this.playGameIaBdd(vBoard, vWinner, nextTurn, currentMode);
                    break;
                case 5 : 
                    vBoard.setClic(true);
                    this.playGameP(vBoard, vWinner, nextTurn, currentMode);
                    break;
            }
        });
        tourIAMinMaxBdd.setRepeats(false);
        tourIAMinMaxBdd.start();
    }

    public void playGameP(VueBoard vBoard, VueWinner vWinner, int turn, int currentMode){
        if((tourIAMinMaxBdd != null)&&tourIAMinMaxBdd.isRunning()) tourIAMinMaxBdd.stop();
        if((tourIAMinMax != null)&&tourIAMinMax.isRunning()) tourIAMinMax.stop();
        if((tourIA != null)&&tourIA.isRunning()) tourIA.stop();
        int winner = victory();
        vBoard.updateGrille(col, ligne, grille, grilleWin);
        if(winner != 0){
            vBoard.updateModeJeu(-1);
            this.afficherWinner(vWinner, winner);
            return;
        }
        if(matchNull()){
            vBoard.updateModeJeu(-1);
            this.afficherWinner(vWinner, -1);
            return;
        }

        currentTurn = turn;
        currentModeP = currentMode;
        currentVBoard = vBoard;
        currentVWinner = vWinner;
        vBoard.setClic(true);
    }

    public void playCvc(VueBoard vBoard, VueWinner vWinner){
        currentModeIA = true;
        vBoard.updateGrille(col, ligne, grille, grilleWin);
        vBoard.updateTurn(player);
        vBoard.updateIndications(2);

        this.playGameC(vBoard, vWinner, player, 0);
    }

    public void playIavia(VueBoard vBoard, VueWinner vWinner){
        currentModeIA = true;
        vBoard.updateGrille(col, ligne, grille, grilleWin);
        vBoard.updateTurn(player);
        vBoard.updateIndications(2);

        this.playGameIa(vBoard, vWinner, player, 1);
    }

    public void playIaviaBdd(VueBoard vBoard, VueWinner vWinner){
        currentModeIA = true;
        vBoard.updateGrille(col, ligne, grille, grilleWin);
        vBoard.updateTurn(player);
        vBoard.updateIndications(2);

        this.playGameIaBdd(vBoard, vWinner, player, 2);
    }

    public void playCvp(VueBoard vBoard, VueWinner vWinner){
        vBoard.updateGrille(col, ligne, grille, grilleWin);
        vBoard.updateTurn(player);

        int start = (int)(Math.random()*2);
        vBoard.updateIndications(start);

        playerTurn = start;

        if(start == 0) this.playGameP(vBoard, vWinner, player, 3);
        else this.playGameC(vBoard, vWinner, player, 3);
    }

    public void playIavp(VueBoard vBoard, VueWinner vWinner){
        vBoard.updateGrille(col, ligne, grille, grilleWin);
        vBoard.updateTurn(player);

        int start = (int)(Math.random()*2);
        vBoard.updateIndications(start);

        playerTurn = start;

        if(start == 0) this.playGameP(vBoard, vWinner, player, 4);
        else this.playGameIa(vBoard, vWinner, player, 4);
    }

    public void playIavpBdd(VueBoard vBoard, VueWinner vWinner){
        vBoard.updateGrille(col, ligne, grille, grilleWin);
        vBoard.updateTurn(player);

        int start = (int)(Math.random()*2);
        vBoard.updateIndications(start);

        playerTurn = start;

        if(start == 0) this.playGameP(vBoard, vWinner, player, 5);
        else this.playGameIaBdd(vBoard, vWinner, player, 5);
    }

    public void playPvp(VueBoard vBoard, VueWinner vWinner){
        vBoard.updateGrille(col, ligne, grille, grilleWin);
        vBoard.updateTurn(player);
        vBoard.updateIndications(2);

        this.playGameP(vBoard, vWinner, player, 6);
    }

    public void startPlay(int mode, VueBoard vBoard, VueWinner vWinner){
        currentModeG = mode;
        currentModeIA = false;

        inGame = true;

        this.initBoard();
        vBoard.updateModeJeu(mode);
        vBoard.initScoreVisibles();

        coups = new ArrayList<>();
        coupsAnnules = new ArrayList<>();

        if((tourIAMinMaxBdd != null)&&tourIAMinMaxBdd.isRunning()) tourIAMinMaxBdd.stop();
        if((tourIAMinMax != null)&&tourIAMinMax.isRunning()) tourIAMinMax.stop();
        if((tourIA != null)&&tourIA.isRunning()) tourIA.stop();
        pauseGame = false;

        switch(mode){
            case 0 : 
                this.playCvc(vBoard, vWinner);
                break;
            case 1 : 
                this.playIavia(vBoard, vWinner);
                break;
            case 2 : 
                this.playIaviaBdd(vBoard, vWinner);
                break;
            case 3 : 
                this.playCvp(vBoard, vWinner);
                break;
            case 4 : 
                this.playIavp(vBoard, vWinner);
                break;
            case 5 : 
                this.playIavpBdd(vBoard, vWinner);
                break;
            case 6 : 
                this.playPvp(vBoard, vWinner);
                break;
        }
    }
}