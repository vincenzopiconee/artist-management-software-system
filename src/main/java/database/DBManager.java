package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Questa classe gestisce la connessione al database.
 */
public class DBManager {

    private static Connection connection = null;

    private DBManager() {}

    /**
     * Restituisce l'unica connessione al database.
     * @return la connessione al database
     * @throws SQLException se si verifica un errore durante la connessione
     */
    public static Connection getConnection() throws SQLException{
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestioneEventi", "root", "");
        }
        return connection;
    }

    /**
     * Chiude la connessione al database.
     * @throws SQLException se si verifica un errore durante la chiusura della connessione
     */
    public static void closeConnection() throws SQLException {
        if (connection != null) {
                connection.close();
        }
    }
}