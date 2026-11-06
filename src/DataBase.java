import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static final String URL = "jdbc:postgresql://localhost:5432/Connect4";
    private static final String USER = "postgres";
    private static final String PASSWORD = "mdp";

    public static Connection getConnexion() throws SQLException, ClassNotFoundException {
        try{
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e){
            throw new RuntimeException("Driver PostgreSQL non reconnu");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}