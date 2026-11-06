import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class BGADomScraper{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    private static final String COLOR_JAUNE = "ffff00";
    private static final String COLOR_ROUGE = "ff0000";

    public BGADomScraper(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(BGAConfig.SELENIUM_TIMEOUT_S));
        this.js = (JavascriptExecutor)driver;
    }

    public int detecterMonNumeroJoueur(){
        try {
            String script =
                "var gd = window.gameui && window.gameui.gamedatas;" +
                "if (!gd) return null;" +
                "var myId = String(window.gameui.player_id);" +
                "var myColor = gd.players[myId] ? gd.players[myId].color : null;" +
                "if (!myColor) return null;" +
                "var order = gd.playerorder;" +
                "for (var i = 0; i < order.length; i++) {" +
                "  var p = gd.players[String(order[i])];" +
                "  if (p && p.color === myColor) return (i + 1) + ':' + myColor;" +
                "}" +
                "return null;";

            Object result = js.executeScript(script);
            if(result != null){
                String[] parts = result.toString().split(":");
                int num = Integer.parseInt(parts[0]);
                String couleur = parts.length > 1 ? parts[1] : "?";
                System.out.println("[Scraper] Joueur n°" + num + " (couleur #" + couleur + ")");
                return num;
            }
        } catch(Exception e){
            System.err.println("[Scraper] Erreur détection joueur : " + e.getMessage());
        }

        System.out.println("[Scraper] ⚠ Détection échouée → valeur par défaut : " + BGAConfig.MY_PLAYER_NUMBER);
        return BGAConfig.MY_PLAYER_NUMBER;
    }

    public boolean estMonTour(int monNumeroJoueur){
        try{
            WebElement body = driver.findElement(By.id("ebd-body"));
            String classes = body.getAttribute("class");
            boolean monTour = classes != null && classes.contains("current_player_is_active");
            if(BGAConfig.DEBUG_DOM) System.out.println("[Tour] ebd-body classes : " + classes);
            return monTour;
        } catch(Exception e){
            System.err.println("[Scraper] Erreur détection tour : " + e.getMessage());
            return false;
        }
    }
    
    public int[][] lireGrille(){
        int[][] grille = new int[BGAConfig.LIGNES][BGAConfig.COLS];

        try{
            chargerCouleurs();
            
            List<WebElement> squares = driver.findElements(By.cssSelector("[id^='square_']"));
            if(squares.isEmpty()){
                System.err.println("[Scraper] ⚠ Aucune cellule square_ trouvée !");
                return grille;
            }

            for(WebElement square : squares){
                String id = square.getAttribute("id");
                if(id == null) continue;
                String[] parts = id.split("_");
                if(parts.length < 3) continue;
                try{
                    int col = Integer.parseInt(parts[1]);
                    int row = Integer.parseInt(parts[2]);
                    if(col < 1 || col > BGAConfig.COLS || row < 1 || row > BGAConfig.LIGNES) continue;

                    int val = 0;
                    List<WebElement> discs = square.findElements(By.cssSelector("[class*='disccolor_']"));
                    if(!discs.isEmpty()){
                        String discClass = discs.get(0).getAttribute("class");
                        val = couleurVersJoueur(discClass);
                    }
                    grille[row - 1][col - 1] = val;
                } catch(NumberFormatException ignored){}
            }

        } catch(Exception e){
            System.err.println("[Scraper] Erreur lecture grille : " + e.getMessage());
        }

        if(BGAConfig.DEBUG_GRILLE) printGrille(grille, "square_COL_ROW");
        return grille;
    }

    private String[] playerColors = null;

    private void chargerCouleurs(){
        try{
            Object result = js.executeScript(
                "var gd = window.gameui && window.gameui.gamedatas;" +
                "if (!gd) return null;" +
                "var order = gd.playerorder;" +
                "var colors = [];" +
                "for (var i = 0; i < order.length; i++) {" +
                "  var p = gd.players[String(order[i])];" +
                "  colors.push(p ? p.color : '');" +
                "}" +
                "return colors.join(',');");
            if(result != null){
                playerColors = result.toString().split(",");
            }
        } catch(Exception e){
            System.err.println("[Scraper] Erreur chargement couleurs : " + e.getMessage());
        }
    }

    private int couleurVersJoueur(String discClass){
        if(discClass == null || playerColors == null) return 0;
        int idx = discClass.indexOf("disccolor_");
        if(idx < 0) return 0;
        String color = discClass.substring(idx + 10).split(" ")[0].trim();
        for(int i = 0; i < playerColors.length; i++){
            if(color.equalsIgnoreCase(playerColors[i])) return i + 1;
        }
        return 0;
    }

    public boolean jouerColonne(int col){
        int bgaCol = col + 1; 

        try{
            Object result = js.executeScript(
                "var col = " + bgaCol + ";" +
                "for (var row = " + BGAConfig.LIGNES + "; row >= 1; row--) {" +
                "  var el = document.getElementById('square_' + col + '_' + row);" +
                "  if (el) {" +
                "    el.click();" +
                "    return 'clicked:square_' + col + '_' + row;" +
                "  }" +
                "}" +
                "return 'not_found';");
            String res = result != null ? result.toString() : "null";
            if(res.startsWith("clicked:")) return true;
            System.err.println("[Scraper] Clic JS : " + res);
        } catch(Exception e){
            System.err.println("[Scraper] Erreur clic colonne " + bgaCol + " : " + e.getMessage());
        }
        return false;
    }

    public boolean partieTerminee(){
        try{
            Object stateName = js.executeScript(
                "var gd = window.gameui && window.gameui.gamedatas;" +
                "return gd && gd.gamestate ? gd.gamestate.name : null;");
            if(stateName != null){
                String name = stateName.toString().toLowerCase();
                if(name.contains("end") || name.contains("finish")){
                    System.out.println("[Scraper] Fin détectée via gamestate : " + name);
                    return true;
                }
            }
            List<WebElement> fins = driver.findElements(By.cssSelector(
                "#score_table, #end_game_panel, .endgame-overlay, #finished_game_result"));
            if(!fins.isEmpty()){
                System.out.println("[Scraper] Fin détectée via DOM.");
                return true;
            }
        } catch(Exception ignored){}
        return false;
    }

    public int getLastMove(){
        try{
            Object result = js.executeScript(
                "var gd = window.gameui && window.gameui.gamedatas;" +
                "if (!gd) return -1;" +
                "var lm = gd.last_move;" +
                "return lm !== undefined ? parseInt(lm) : -1;");
            if(result != null) return ((Number) result).intValue();
        } catch(Exception e){
            System.err.println("[Scraper] Erreur getLastMove : " + e.getMessage());
        }
        return -1;
    }

    public int[][] lireGrilleSilencieuse(){
        boolean old = BGAConfig.DEBUG_GRILLE;
        int[][] grille = new int[BGAConfig.LIGNES][BGAConfig.COLS];
        try{
            chargerCouleurs();
            java.util.List<WebElement> squares = driver.findElements(By.cssSelector("[id^='square_']"));
            for(WebElement square : squares){
                String id = square.getAttribute("id");
                if(id == null) continue;
                String[] parts = id.split("_");
                if(parts.length < 3) continue;
                try{
                    int col = Integer.parseInt(parts[1]);
                    int row = Integer.parseInt(parts[2]);
                    if(col < 1 || col > BGAConfig.COLS || row < 1 || row > BGAConfig.LIGNES) continue;
                    int val = 0;
                    java.util.List<WebElement> discs = square.findElements(By.cssSelector("[class*='disccolor_']"));
                    if(!discs.isEmpty()) val = couleurVersJoueur(discs.get(0).getAttribute("class"));
                    grille[row - 1][col - 1] = val;
                } catch(NumberFormatException ignored){}
            }
        } catch(Exception e){
            System.err.println("[Scraper] Erreur lireGrilleSilencieuse : " + e.getMessage());
        }
        return grille;
    }

    public void printGrille(int[][] grille, String source){
        System.out.println("=== Grille (" + source + ") === (haut=row1 BGA)");
        for(int i = 0; i < BGAConfig.LIGNES; i++){
            StringBuilder row = new StringBuilder("| ");
            for(int j = 0; j < BGAConfig.COLS; j++){
                switch(grille[i][j]){
                    case 0: row.append(". "); break;
                    case 1: row.append("R "); break;
                    case 2: row.append("J "); break;
                    default: row.append("? "); break;
                }
            }
            row.append("| row" + (i + 1));
            System.out.println(row);
        }
        StringBuilder colNums = new StringBuilder("  ");
        for(int j = 1; j <= BGAConfig.COLS; j++) colNums.append(j).append(" ");
        System.out.println(colNums);
    }
}
