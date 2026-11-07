package boundary;

import exception.LoginException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Login {
    private static Connection connection = null;

    private Login() {}

    //Metodi per ottenere e chiudere la connessione al database
    //Privati perchè servono solo per fare il login
    private static Connection getConnection() throws SQLException{
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestioneEventi", "root", "");
        }
        return connection;
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
                connection.close();
        }
    }

    //Ritorna l'id del musicista se la password è corretta
    public static int CheckPassword(String email, String password) throws LoginException {
        int id = -1;
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT idmusicista, password FROM musicisti WHERE email = ?");
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String pw = rs.getString("password");
                    if (password.equals(pw)) {
                        id = rs.getInt("idmusicista");
                    } else {
                        throw new LoginException("Password sbagliata");
                    }
                } else {
                    throw new LoginException("Utente non esistente");
                }
            } catch (SQLException e) {
                throw new LoginException("Email sbagliata");
            } finally {
                closeConnection();
            }
        } catch (SQLException e) {
            throw new LoginException("Errore di connessione");
        }
        return id;
    }
}
