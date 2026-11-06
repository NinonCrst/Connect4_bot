public class Evaluation {
    int[][] grille;
    int player;
    int ligne; 
    int col;

    public int evaluate(int[][] grille, int player, int ligne, int col){
        this.grille = grille;
        this.player = player;
        this.ligne = ligne;
        this.col = col;

        int score = 0;
        score += eval_width();
        score += eval_height();
        score += eval_diagUD();
        score += eval_diagDU();
        return score;
    }

    public int eval_width(){
        int score = 0;
        int enemy = 3-player;
        int count_j;
        int count_e;

        for(int i = 0; i<ligne; i++){
            for(int j = 0; j<col-3; j++){
                count_j = 0;
                count_e = 0;

                for(int k = 0; k<4; k++){
                    if(grille[i][j+k] == player) count_j++;
                    else if(grille[i][j+k] == enemy) count_e++;
                }

                if(count_e == 0){
                    if(count_j == 1) score++;
                    else if(count_j == 2) score += 5;
                    else if(count_j == 3) score += 20;
                    else if(count_j == 4) score += 999;
                }

                if(count_j == 0){
                    if(count_e == 2) score -= 5;
                    else if(count_e == 3) score -= 20;
                    else if(count_e == 4) score -= 999;
                }
            }
        }
        return score;
    }

    public int eval_height(){
        int score = 0;
        int enemy = 3-player;
        int count_j;
        int count_e;

        for(int i = 0; i<ligne-3; i++){
            for(int j = 0; j<col; j++){
                count_j = 0;
                count_e = 0;

                for(int k = 0; k<4; k++){
                    if(grille[i+k][j] == player) count_j++;
                    else if(grille[i+k][j] == enemy) count_e++;
                }

                if(count_e == 0){
                    if(count_j == 1) score++;
                    else if(count_j == 2) score += 5;
                    else if(count_j == 3) score += 20;
                    else if(count_j == 4) score += 999;
                }

                if(count_j == 0){
                    if(count_e == 2) score -= 5;
                    else if(count_e == 3) score -= 20;
                    else if(count_e == 4) score -= 999;
                }
            }
        }
        return score;
    }

    public int eval_diagUD(){
        int score = 0;
        int enemy = 3-player;
        int count_j;
        int count_e;

        for(int i = 0; i<ligne-3; i++){
            for(int j = 0; j<col-3; j++){
                count_j = 0;
                count_e = 0;

                for(int k = 0; k<4; k++){
                    if(grille[i+k][j+k] == player) count_j++;
                    else if(grille[i+k][j+k] == enemy) count_e++;
                }

                if(count_e == 0){
                    if(count_j == 1) score++;
                    else if(count_j == 2) score += 5;
                    else if(count_j == 3) score += 20;
                    else if(count_j == 4) score += 999;
                }

                if(count_j == 0){
                    if(count_e == 2) score -= 5;
                    else if(count_e == 3) score -= 20;
                    else if(count_e == 4) score -= 999;
                }
            }
        }
        return score;
    }

    public int eval_diagDU(){
        int score = 0;
        int enemy = 3-player;
        int count_j;
        int count_e;

        for(int i = 3; i<ligne; i++){
            for(int j = 0; j<col-3; j++){
                count_j = 0;
                count_e = 0;

                for(int k = 0; k<4; k++){
                    if(grille[i-k][j+k] == player) count_j++;
                    else if(grille[i-k][j+k] == enemy) count_e++;
                }

                if(count_e == 0){
                    if(count_j == 1) score++;
                    else if(count_j == 2) score += 5;
                    else if(count_j == 3) score += 20;
                    else if(count_j == 4) score += 999;
                }

                if(count_j == 0){
                    if(count_e == 2) score -= 5;
                    else if(count_e == 3) score -= 20;
                    else if(count_e == 4) score -= 999;
                }
            }
        }
        return score;
    }
}