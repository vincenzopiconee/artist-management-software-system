package database;

import entity.Concerto;
import entity.Esibizione;
import entity.Evento;
import entity.Strumentale;
import exception.DatabaseException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe gestisce la comunicazione con il database per oggetti di tipo evento.
 */
public class EventoDAO{

    public static void updateEvento() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    public static void deleteEvento() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    /**
     * Inserisce un nuovo evento nel database con i dettagli necessari.
     * L'id dell'evento e lo stato non sono passati come parametri in quanto vengono impostati automaticamente dal database.
     * 
     * @param data La data dell'evento nel formato "yyyy-MM-dd".
     * @param luogo Il luogo dell'evento.
     * @param tipologia La tipologia dell'evento.
     * @param costoTotale Il costo totale dell'evento.
     * @param musicistiIncerti Il numero di musicisti incerti per l'evento.
     * @throws DatabaseException Se si verifica un errore durante l'inserimento dell'evento o la connessione al database.
     */
    private static void inserisciEvento(String data, String luogo, String tipologia, float costoTotale, int musicistiIncerti) throws DatabaseException {
        try {
            Connection conn = DBManager.getConnection();
            String query = "INSERT INTO Eventi(Data, Luogo, Tipologia, CostoTotale, MusicistiIncerti) VALUES (?, ?, ?, ?, ?)";
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, data);
                ps.setString(2, luogo);
                ps.setString(3, tipologia);
                ps.setFloat(4, costoTotale);
                ps.setInt(5, musicistiIncerti);                
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Errore durante l'inserimento dell'evento");
            } finally {
                DBManager.closeConnection();
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore di connessione al database");
        }
    }

    /**
     * Restituisce l'ID dell'evento appena aggiunto (più recente) nel database.
     * 
     * @return l'ID dell'evento più recente, o -1 se non ci sono eventi nel database
     * @throws DatabaseException se si verifica un errore durante la ricerca dell'evento o la connessione al database
     */
    private static int getIdEvento() throws DatabaseException{
        int id = -1;
        try {
            Connection conn = DBManager.getConnection();
            String query = "SELECT MAX(idEvento) FROM eventi";
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                return id;
            } catch (SQLException e) {
                throw new DatabaseException("Errore durante la ricerca dell'evento");
            } finally {
                DBManager.closeConnection();
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore di connessione al database");
        }
    }

    /**
     * Crea un nuovo evento nel database con le informazioni fornite, inserendo anche le partecipazioni dei musicisti convocati.
     * 
     * @param data La data dell'evento nel formato "yyyy-MM-dd".
     * @param luogo Il luogo dell'evento.
     * @param tipologia La tipologia dell'evento.
     * @param idMusicistiSelezionati Una lista di interi che rappresentano gli id dei musicisti selezionati per l'evento.
     * @param costoTotale Il costo totale dell'evento.
     * @param musicistiIncerti Il numero di musicisti incerti per l'evento.
     * @throws DatabaseException Se si verifica un errore durante l'inserimento dell'evento o dei musicisti.
     */
    public static void createEvento(String data, String luogo, String tipologia, List<Integer> idMusicistiSelezionati, float costoTotale, int musicistiIncerti) throws DatabaseException {
        try {
            inserisciEvento(data, luogo, tipologia, costoTotale, musicistiIncerti);
            int idEvento = getIdEvento();
            Connection conn = DBManager.getConnection();
            try {
                String query = "INSERT INTO Partecipazioni(IdEvento, IdMusicista) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(query);
                for (Integer musicistaSelezionato : idMusicistiSelezionati) {
                    ps.setInt(1, idEvento);
                    ps.setInt(2, musicistaSelezionato);
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                throw new DatabaseException("Errore durante l'inserimento dei musicisti");
            } finally {
                DBManager.closeConnection();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante l'inserimento dell'evento");
        }   
    }

    /**
     * Aggiorna il numero di musicisti incerti (musicisti ancora da confermare) per un determinato evento nel database.
     * 
     * @param idEvento L'ID dell'evento da aggiornare.
     * @param numMusicistiIncerti Il nuovo numero di musicisti incerti.
     * @throws DatabaseException Se si verifica un errore durante l'aggiornamento del numero di musicisti incerti.
     */
    public static void updateMusicistiIncerti(Integer idEvento, Integer numMusicistiIncerti) throws DatabaseException {
        try {
            Connection conn = DBManager.getConnection();
            String query = "UPDATE eventi SET musicistiIncerti = ? WHERE idEvento = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, numMusicistiIncerti);
                ps.setInt(2, idEvento);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Errore durante l'aggiornamento del numero di musicisti incerti");
            } finally {
                DBManager.closeConnection();
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore di connessione al database");
        }
    }

    /**
     * Legge un evento dal database dato l'ID dell'evento.
     * Restituisce un oggetto di tipo Evento corrispondente all'ID passato.
     * 
     * @param idEvento l'ID dell'evento da leggere
     * @return l'oggetto Evento corrispondente all'ID passato, o null se l'evento non esiste
     * @throws DatabaseException se si verifica un errore durante la lettura dell'evento o la connessione al database
     */
    public static Evento readEvento(int idEvento) throws DatabaseException {
        Evento e = null;
        try {
			Connection conn = DBManager.getConnection();
			String query = "SELECT * FROM eventi WHERE idevento = ?";
			try {
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setInt(1, idEvento);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {	
					String tipologia = rs.getString(4);
                    //In base alla tipologia dell'evento istanzio un oggetto di tipo Concerto, Strumentale o Esibizione
					if (tipologia.equalsIgnoreCase("concerto")) {
						e = new Concerto(idEvento, rs.getString(2), rs.getString(3), rs.getFloat(5), rs.getInt(6), null , null);
					}
					else if (tipologia.equalsIgnoreCase("strumentale")) {
						e = new Strumentale(idEvento, rs.getString(2), rs.getString(3), rs.getFloat(5), rs.getInt(6), null);
					}
					else if (tipologia.equalsIgnoreCase("esibizione")) {
						e = new Esibizione(idEvento, rs.getString(2), rs.getString(3), rs.getFloat(5), rs.getInt(6), null , null);
					}
					return e;
				}
			} catch (SQLException e2) {
				throw new DatabaseException("Errore durante la lettura dell'evento");
			} finally {
				DBManager.closeConnection();
			}
		} catch (SQLException ex) {
			throw new DatabaseException("Errore di connessione al database");
		}
        return null;
    }
    
    /**
     * Legge gli eventi del mese e anno passati dal database.
     * 
     * @param mese Il numero del mese (1-12).
     * @param anno L'anno.
     * @return Una lista di oggetti Evento corrispondenti agli eventi avvenuti durante il mese e l'anno passati.
     * @throws DatabaseException Se si verifica un errore durante la lettura o la connessione al database.
     */
    public static List<Evento> readEventiMensili(int mese, int anno) throws DatabaseException{
        List<Evento> eventi = new ArrayList<>();
        try {
            Connection conn = DBManager.getConnection();
            String query = "SELECT idEvento, data, luogo, costoTotale, tipologia FROM eventi WHERE stato='CONFERMATO' AND data BETWEEN ? AND ?";
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setDate(1, Date.valueOf(String.valueOf(anno) + "-" + String.valueOf(mese) + "-" + "01"));
                ps.setDate(2, Date.valueOf(String.valueOf(anno) + "-" + String.valueOf(mese+1) + "-" + "01"));
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String tipologia = rs.getString(5);
                    if (tipologia.equalsIgnoreCase("concerto")) {
                        Evento evento = new Concerto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), 0,null, null);
                        eventi.add(evento);
                    }
                    else if (tipologia.equalsIgnoreCase("strumentale")) {
                        Evento evento = new Strumentale(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), 0,null);
                        eventi.add(evento);
                    }
                    else if (tipologia.equalsIgnoreCase("esibizione")){
                        Evento evento = new Esibizione(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), 0, null, null);
                        eventi.add(evento);
                    }
                }
                return eventi;
            } catch (SQLException e) {
                throw new DatabaseException("Errore durante la ricerca dell'evento");
            } finally {
                DBManager.closeConnection();
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore di connessione al database");
        }
    }
    
    /**
     * Conferma lo stato di un evento specificato tramite l'ID dell'evento.
     * L'evento verrà contrassegnato come "CONFERMATO" nel database.
     * 
     * @param idEvento l'ID dell'evento da confermare
     * @throws DatabaseException se si verifica un errore durante la conferma dello stato dell'evento o la connessione al database
     */
    public static void confermaStato(int idEvento) throws DatabaseException{
        try {
            Connection conn = DBManager.getConnection();
            String query = "UPDATE eventi SET stato='CONFERMATO' WHERE idEvento=?";
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, idEvento);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Errore durante la conferma dello stato dell'evento");
            } finally {
                DBManager.closeConnection();
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore di connessione al database");
        }
    }
}