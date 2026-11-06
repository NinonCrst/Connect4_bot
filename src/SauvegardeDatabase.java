import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SauvegardeDatabase {
    private Gson gson = new Gson();

    public boolean saveDatabase(Sauvegarde save){
        String insert = "INSERT INTO s01.partie (firstplayer, ligne, col, num, currentturn, tourjoueur, mode, confiance, statut, grille, grillewin, coups) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?::json, ?::json, ?::json)";

        try(Connection co = DataBase.getConnexion()){
            try(PreparedStatement stmt = co.prepareStatement(insert)){
                String grilleConvert = gson.toJson(save.getGrilleSave());
                String grilleWinConvert = gson.toJson(save.getGrilleWinSave());
                String coupsConvert = gson.toJson(save.getCoupsSave());

                stmt.setInt(1, save.getFirstPlayerSave());
                stmt.setInt(2, save.getLigneSave());
                stmt.setInt(3, save.getColSave());
                stmt.setInt(4, save.getIndexSave());
                stmt.setInt(5, save.getCurrentTurnSave());
                stmt.setInt(6, save.getTourJoueurSave());
                stmt.setInt(7, save.getModeSave());
                stmt.setDouble(8, save.getConfiance());
                stmt.setBoolean(9, save.getStatutSave());
                stmt.setString(10, grilleConvert);
                stmt.setString(11, grilleWinConvert);
                stmt.setString(12, coupsConvert);

                stmt.executeUpdate();

                try(PreparedStatement stmtSituation = co.prepareStatement("SELECT s01.insert_situation(?::jsonb, ?::jsonb, ?)")){
                    stmtSituation.setString(1, grilleConvert);
                    stmtSituation.setString(2, coupsConvert);
                    stmtSituation.setInt(3, save.getIndexSave());
                    stmtSituation.execute();
                } catch(PSQLException e){
                    if("23505".equals(e.getSQLState())){
                        JOptionPane.showMessageDialog(null, "Cette partie existe déjà dans la base de données !", "Sauvegarde impossible", JOptionPane.ERROR_MESSAGE);
                        return false;
                    } 
                    else e.printStackTrace();
                } catch(Exception e){
                    e.printStackTrace();
                }
            } catch(PSQLException e){
                if("23505".equals(e.getSQLState())){
                    JOptionPane.showMessageDialog(null, "Cette partie existe déjà dans la base de données !", "Sauvegarde impossible", JOptionPane.ERROR_MESSAGE);
                    return false;
                } 
                else e.printStackTrace();
            } catch(Exception e){
                e.printStackTrace();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public List<Sauvegarde> openDatabase(){
        List<Sauvegarde> allSaves = new ArrayList<>();
        String requete = "SELECT * FROM s01.partie ORDER BY num ASC";

        try(Connection co = DataBase.getConnexion()){
            try(PreparedStatement stmt = co.prepareStatement(requete)){
                try(ResultSet result = stmt.executeQuery()){
                    while(result.next()){
                        int[][] grille = gson.fromJson(result.getString("grille"), int[][].class);
                        boolean[][] grilleWin = gson.fromJson(result.getString("grillewin"), boolean[][].class);
                        
                        Type typeCoups = new TypeToken<List<Coup>>(){}.getType();
                        List<Coup> coups = gson.fromJson(result.getString("coups"), typeCoups);

                        Sauvegarde save = new Sauvegarde(result.getInt("firstplayer"), result.getInt("ligne"), result.getInt("col"), result.getInt("num"), result.getInt("currentturn"), result.getInt("tourjoueur"), result.getInt("mode"), result.getInt("confiance"),  result.getBoolean("statut"), grille, grilleWin, coups);
                        allSaves.add(save);
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            } catch(Exception e){
                    e.printStackTrace();
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return allSaves;
    }

    public List<Sauvegarde> openDatabaseOutils(){
        List<Sauvegarde> allSaves = new ArrayList<>();
        String request = "SELECT partie.*, s_cano.grille AS canogrille, s_sym.grille AS symgrille FROM s01.partie AS partie LEFT JOIN s01.situation AS s_cano ON partie.num = s_cano.num AND s_cano.sym = FALSE LEFT JOIN s01.situation AS s_sym ON partie.num = s_sym.num AND s_sym.sym = TRUE ORDER BY partie.num ASC";

        try(Connection co = DataBase.getConnexion()){
            try(PreparedStatement stmt = co.prepareStatement(request)){
                try(ResultSet result = stmt.executeQuery()){
                    while(result.next()){
                        int[][] grille = gson.fromJson(result.getString("canogrille"), int[][].class);
                        int[][] sym = gson.fromJson(result.getString("symgrille"), int[][].class);
                        boolean[][] grilleWin = gson.fromJson(result.getString("grilleWin"), boolean[][].class);
                        
                        Type typeCoups = new TypeToken<List<Coup>>(){}.getType();
                        List<Coup> coups = gson.fromJson(result.getString("coups"), typeCoups);

                        Sauvegarde save = new Sauvegarde(result.getInt("firstplayer"), result.getInt("ligne"), result.getInt("col"), result.getInt("num"), result.getInt("currentturn"), result.getInt("tourjoueur"), result.getInt("mode"), result.getInt("confiance"), result.getBoolean("statut"), grille, sym, grilleWin, coups);
                        allSaves.add(save);
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            } catch(Exception e){
                    e.printStackTrace();
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return allSaves;
    }

    public Sauvegarde openData(int id){
        Sauvegarde save = null;
        String requete = "SELECT * FROM s01.partie WHERE num = ?";

        try(Connection co = DataBase.getConnexion()){
            try(PreparedStatement stmt = co.prepareStatement(requete)){
                stmt.setInt(1, id);
                try(ResultSet result = stmt.executeQuery()){
                    if(result.next()){
                        int[][] grille = gson.fromJson(result.getString("grille"), int[][].class);
                        boolean[][] grilleWin = gson.fromJson(result.getString("grillewin"), boolean[][].class);
                        
                        Type typeCoups = new TypeToken<List<Coup>>(){}.getType();
                        List<Coup> coups = gson.fromJson(result.getString("coups"), typeCoups);

                        save = new Sauvegarde(result.getInt("firstplayer"), result.getInt("ligne"), result.getInt("col"), result.getInt("num"), result.getInt("currentturn"), result.getInt("tourjoueur"), result.getInt("mode"), result.getInt("confiance"), result.getBoolean("statut"), grille, grilleWin, coups);
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return save;
    }

    public List<Sauvegarde> openDataOutilsBdd(int id){
        List<Sauvegarde> saveCS = new ArrayList<>();
        String requete = "SELECT partie.*, ssym.grille AS symgrille, scano.grille AS canogrille, ssym.coups AS symcoups, scano.coups AS canocoups FROM s01.partie AS partie LEFT JOIN s01.situation AS ssym ON partie.num = ssym.num AND ssym.sym = TRUE LEFT JOIN s01.situation AS scano ON partie.num = scano.num AND scano.sym = FALSE WHERE partie.num = ?";

        try(Connection co = DataBase.getConnexion()){
            try(PreparedStatement stmt = co.prepareStatement(requete)){
                stmt.setInt(1, id);
                try(ResultSet result = stmt.executeQuery()){
                    if(result.next()){
                        int[][] grille = gson.fromJson(result.getString("canogrille"), int[][].class);
                        int[][] sym = gson.fromJson(result.getString("symgrille"), int[][].class);
                        boolean[][] grilleWin = gson.fromJson(result.getString("grillewin"), boolean[][].class);
                        
                        Type typeCoups = new TypeToken<List<Coup>>(){}.getType();
                        List<Coup> coups = gson.fromJson(result.getString("canocoups"), typeCoups);
                        List<Coup> coups_sym = gson.fromJson(result.getString("symcoups"), typeCoups);

                        Sauvegarde saveC = new Sauvegarde(result.getInt("firstplayer"), result.getInt("ligne"), result.getInt("col"), result.getInt("num"), result.getInt("currentturn"), result.getInt("tourjoueur"), result.getInt("mode"), result.getInt("confiance"), result.getBoolean("statut"), grille, grilleWin, coups);
                        Sauvegarde saveS = new Sauvegarde(result.getInt("firstplayer"), result.getInt("ligne"), result.getInt("col"), result.getInt("num"), result.getInt("currentturn"), result.getInt("tourjoueur"), result.getInt("mode"), result.getInt("confiance"), result.getBoolean("statut"), sym, grilleWin, coups_sym);
                        saveCS.add(saveC);
                        saveCS.add(saveS);
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return saveCS;
    }

    public Coup openCoup(List<Coup> coups){
        List<Coup> res = new ArrayList<>();
        Coup coup;
        String requete = "SELECT situation.coups AS situ_coups FROM s01.situation AS situation LEFT JOIN s01.partie AS partie ON situation.num = partie.num WHERE situation.coups::text LIKE CONCAT(?::text, '%') AND partie.statut = TRUE ORDER BY partie.confiance DESC LIMIT 1";

        String coupsConvert = gson.toJson(coups);
        try(Connection co = DataBase.getConnexion()){
            try(PreparedStatement stmt = co.prepareStatement(requete)){
                stmt.setString(1, coupsConvert);
                try(ResultSet result = stmt.executeQuery()){
                    if(result.next()){
                        Type typeCoups = new TypeToken<List<Coup>>(){}.getType();
                        res = gson.fromJson(result.getString("situ_coups"), typeCoups);
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        if((res == null) || (res.size()<=coups.size())) return null;
        coup = res.get(coups.size());
        return coup;
    }
}