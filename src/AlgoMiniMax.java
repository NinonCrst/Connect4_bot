public class AlgoMiniMax {
    Evaluation eval;
    int[][] copieGrille;
    int ligne;
    int col;
    int player;
    int[] scoreTab;

    public AlgoMiniMax(){
        this.eval = new Evaluation();
    }

    public int calculCoup(int[][] grille, int profondeur, int ligne, int col, int player){
        this.ligne = ligne;
        this.col = col;
        this.player = player;
        this.scoreTab = new int[col];

        int bestScore = Integer.MIN_VALUE;
        int bestCol = -1;
        int ligneChoix;
        int score;

        for(int j = 0; j<col; j++){
            copieGrille = copyGrille(grille);
            ligneChoix = simulation(j, this.player);

            if(ligneChoix != -1){
                score = fctMiniMax(profondeur-1, false);
                scoreTab[j] = score;
                if(score > bestScore){
                    bestScore = score;
                    bestCol = j;
                }
            }
        }
        return bestCol;
    }

    public int[][] copyGrille(int[][] grille){
        int[][] copy = new int[ligne][col];

        for(int i = 0; i<ligne; i++){
            for(int j = 0; j<col; j++){
                copy[i][j] = grille[i][j];
            }
        }
        return copy;
    }

    public boolean matchNull(){
        for(int i = 0; i<ligne; i++){
            for(int j = 0; j<col; j++){
                if(copieGrille[i][j] == 0) return false;
            }
        }
        return true;
    }

    public boolean victory(int player){
        for(int i = 0; i<ligne; i++){
            for(int j = 0; j<col-3; j++){
                if((copieGrille[i][j] == player)&&(copieGrille[i][j+1] == player)&&(copieGrille[i][j+2] == player)&&(copieGrille[i][j+3] == player)) return true;
            }
        }
        
        for(int i = 0; i<ligne-3; i++){
            for(int j = 0; j<col; j++){
                if((copieGrille[i][j] == player)&&(copieGrille[i+1][j] == player)&&(copieGrille[i+2][j] == player)&&(copieGrille[i+3][j] == player)) return true;
            }
        }
        
        for(int i = 0; i<ligne-3; i++){
            for(int j = 0; j<col-3; j++){
                if((copieGrille[i][j] == player)&&(copieGrille[i+1][j+1] == player)&&(copieGrille[i+2][j+2] == player)&&(copieGrille[i+3][j+3] == player)) return true;
            }
        }
        
        for(int i = 3; i<ligne; i++){
            for(int j = 0; j<col-3; j++){
                if((copieGrille[i][j] == player)&&(copieGrille[i-1][j+1] == player)&&(copieGrille[i-2][j+2] == player)&&(copieGrille[i-3][j+3] == player)) return true;
            }
        }
        return false;
    }

    public int simulation(int col, int player){
        for(int i = ligne-1; i>=0; i--){
            if(copieGrille[i][col] == 0){
                copieGrille[i][col] = player;
                return i;
            }
        }
        return -1;
    }

    public void annulation(int ligne, int col){
        copieGrille[ligne][col] = 0;
    }

    public int fctMiniMax(int profondeur, boolean thisTurn){
        if(victory(this.player)) return 100000+profondeur;
        if(victory(3-this.player)) return -100000-profondeur;
        if(profondeur == 0) return eval.evaluate(copieGrille, this.player, ligne, col);
        if(matchNull()) return 0;
        if(thisTurn){
            int bestScore = Integer.MIN_VALUE;
            int score;

            for(int j = 0; j<col; j++){
                int ligneChoix = simulation(j, this.player);

                if(ligneChoix != -1){
                    score = fctMiniMax(profondeur-1, false);
                    annulation(ligneChoix, j);
                    if(score > bestScore) bestScore = score;
                }
            }
            return bestScore;
        }

        else{
            int bestScore = Integer.MAX_VALUE;
            int score;

            for(int j = 0; j<col; j++){
                int ligneChoix = simulation(j, 3-this.player);

                if(ligneChoix != -1){
                    score = fctMiniMax(profondeur-1, true);
                    annulation(ligneChoix, j);
                    if(score < bestScore) bestScore = score;
                }
            }
            return bestScore;
        }
    }

    public int[] getScoreTab(){
        return this.scoreTab;
    }

    public int getScoreCol(int[][] tableau, int prof, int coln, int play){
        this.copieGrille = copyGrille(tableau);
        int line = simulation(coln, play);

        if(line == -1) return (-500000);
        return fctMiniMax(prof-1, false);
    }

    public double calculConfiance(int profondeurMax){
        int bestScore = Integer.MIN_VALUE;
        int secondScore = Integer.MIN_VALUE;
        int worstScore = Integer.MAX_VALUE;

        for(int i = 0; i<scoreTab.length; i++){
            if(scoreTab[i] > bestScore){
                secondScore = bestScore;
                bestScore = scoreTab[i];
            }
            else if(scoreTab[i] > secondScore) secondScore = scoreTab[i];
            if(scoreTab[i] < worstScore) worstScore = scoreTab[i]; 
        }

        if(bestScore == Integer.MIN_VALUE) return 0.0;

        double ecart = 0.0;
        if(Math.abs(bestScore) > 0) ecart = (double)(bestScore - secondScore)/ Math.abs(bestScore);
        ecart = Math.max(0.0, Math.min(1.0,ecart));

        double scale = bestScore - worstScore;
        double coherence = 1.0 - (scale/(Math.abs(bestScore)+1.0));
        coherence = Math.max(0.0, Math.min(1.0,coherence));

        double prof = (double)(profondeurMax - 1)/profondeurMax;
        return 0.5*ecart + 0.3*coherence + 0.2*prof;
    }

    public int convertConfiance(double conf){
        if(conf < 0.1) return 0;
        if(conf < 0.25) return 1;
        if(conf < 0.45) return 2;
        if(conf < 0.65) return 3;
        if(conf < 0.85) return 4;
        return 5;
    }
}