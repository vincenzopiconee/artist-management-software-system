package database;

import entity.Cantante;
import entity.Musicista;
import entity.Strumentista;
import exception.DatabaseException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Questa classe gestisce la comunicazione con il database per oggetti di tipo Musicista.
 */
public class MusicistaDAO {

    public static void createMusicista() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Non supportato.");
    }

    public static void readMusicista() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Non supportato.");
    }

    public static void updateMusicista() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Non supportato.");
    }

    public static void deleteMusicista() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Non supportato.");
    }

    /**
     * Legge il nome e il cognome di un musicista dato il suo ID.
     * 
     * @param idMusicista l'ID del musicista
     * @return il nome e il cognome del musicista separati da uno spazio
     * @throws DatabaseException se si verifica un errore durante la lettura o la connessione al database
     */
    public static String readNomeCognome(int idMusicista) throws DatabaseException {
        String nome = "";
        String cognome = "";
        try {
			Connection conn = DBManager.getConnection();
			String query = "SELECT Nome, Cognome FROM Musicisti WHERE IdMusicista = ?";
			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, idMusicista);
				ResultSet result = stmt.executeQuery();
				if(result.next()) {
					nome = result.getString(1);	
                    cognome = result.getString(2);
				}
			}catch(SQLException e) {
				throw new DatabaseException("Errore lettura nome e cognome del musicista");
			} finally {
				DBManager.closeConnection();
			}
        }catch(SQLException e) {
			throw new DatabaseException("Errore di connessione database");
		}
        return nome + " " + cognome;
    }

    /**
     * Legge i compensi dei musicisti per il mese precedente.
     * Restituisce una mappa che associa il nome completo del musicista al suo compenso totale.
     *
     * @param mese il mese di riferimento (1-12)
     * @param anno l'anno di riferimento
     * @return una mappa che associa il nome completo del musicista al suo compenso
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    public static HashMap<String,Float> readCompensiMensili(int mese, int anno) throws DatabaseException{
        HashMap<String,Float> compensiMesePrecedente = new HashMap<>();
        try{
            Connection conn = DBManager.getConnection();
            String vista = "CREATE VIEW Temp(IdMusicista, N_Eventi) as SELECT P.IdMusicista, COUNT(E.IdEvento) FROM Partecipazioni P JOIN Eventi E ON P.IdEvento=E.IdEvento WHERE E.Data BETWEEN ? AND ? AND E.Stato='CONFERMATO' GROUP BY P.IdMusicista";
            String query = "SELECT M.Nome, M.Cognome, T.N_Eventi*M.Cachet AS Compenso FROM Musicisti M JOIN Temp T ON M.IdMusicista=T.IdMusicista";
            try{
                PreparedStatement stmt = conn.prepareStatement(vista);
                stmt.setDate(1, Date.valueOf(String.valueOf(anno) + "-" + String.valueOf(mese) + "-" + "01"));
                stmt.setDate(2, Date.valueOf(String.valueOf(anno) + "-" + String.valueOf(mese+1) + "-" + "01"));
                stmt.execute();
                PreparedStatement stmt2 = conn.prepareStatement(query);
                ResultSet result = stmt2.executeQuery();
                while(result.next()) {
                    compensiMesePrecedente.put(result.getString(1)+ " " + result.getString(2), result.getFloat(3));
                }
                PreparedStatement stmt3 = conn.prepareStatement("DROP VIEW Temp");
                stmt3.execute();
            } catch(SQLException e) {
				throw new DatabaseException("Errore lettura compensi musicisti");
			} finally {
				DBManager.closeConnection();
			}
        } catch(SQLException e){
            throw new DatabaseException("Errore di connessione database");
        }
        return compensiMesePrecedente;
    }

    /**
     * Legge i musicisti disponibili per una determinata data.
     * 
     * @param data La data nel formato "yyyy-MM-dd" per cui si vogliono ottenere i musicisti disponibili
     * @return Una lista di oggetti Musicista che rappresentano i musicisti disponibili
     * @throws DatabaseException Se si verifica un errore durante l'accesso al database
     */
    public static List<Musicista> readMusicistiDisponibili(String data) throws DatabaseException{
        List<Musicista> musicistiDisponibili = new ArrayList<>();
        try{
            Connection conn = DBManager.getConnection();
            //Seleziono gli attributi di interesse per tutti i musicisti disponibili nella data passata come parametro
            String query = "SELECT M.IdMusicista, M.Nome, M.Cognome, M.Cachet, M.Email, M.Specializzazione FROM Disponibility D JOIN Musicisti M ON D.IdMusicista = M.IdMusicista WHERE Data = ?";
            try{
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, data);
                ResultSet result = stmt.executeQuery();                
                while(result.next()) {
                    if(result.getString(6).equals("Cantante")){
                        //Per i cantanti bisogna anche prendersi il genere musicale e il registro vocale (nel caso di genere musicale classico)
                        String query2 = "SELECT C.GenereMusicale, C.RegistroVocale FROM GenereMusicaleCantanti as C WHERE IdMusicista = ?";
                        PreparedStatement stmt2 = conn.prepareStatement(query2);
                        stmt2.setInt(1, result.getInt(1));
                        ResultSet result2 = stmt2.executeQuery();
                        if (result2.next()) {
                            Cantante c = new Cantante(result.getInt(1), result.getString(5), result.getString(2), result.getString(3), null, null, result.getFloat(4));
                            switch (result2.getString(1)) {
                                case "Classica":
                                    c.setCantanteClassico(result2.getString(2));
                                    break;
                                case "Leggera":
                                    c.setCantanteLeggero();
                                    break;
                                case "Pop":
                                    c.setCantantePop();
                                    break;
                                case "Jazz":
                                    c.setCantanteJazz();
                                    break;
                                default:
                                    throw new DatabaseException("Errore dati nel database");
                            }
                            musicistiDisponibili.add(c);
                        }
                    } else {
                        //Altrimenti si tratta di uno strumentista, quindi bisogna prendersi gli strumenti suonati
                        String query3 = "SELECT Strumento FROM StrumentiMusicisti WHERE IdMusicista = ? ";
                        PreparedStatement stmt3 = conn.prepareStatement(query3);
                        stmt3.setInt(1, result.getInt(1));
                        ResultSet result3 = stmt3.executeQuery();
                        List<String> strumenti = new ArrayList<>();
                        while(result3.next()) {
                            strumenti.add(result3.getString(1));
                        }
                        Strumentista s = new Strumentista(result.getInt(1), result.getString(5), result.getString(2), result.getString(3), null, null, result.getFloat(4), strumenti);
                        musicistiDisponibili.add(s);
                    }
                }
            } catch(SQLException e) {
				throw new DatabaseException("Errore lettura musicisti disponibili: " + e.getMessage());
			} finally {
				DBManager.closeConnection();
			}
        } catch(SQLException e){
            throw new DatabaseException("Errore di connessione database:" + e.getMessage());
        }
        return musicistiDisponibili;
    }

    /**
     * Restituisce un HashMap contenente le informazioni delle convocazioni per un determinato musicista.
     * Per ogni evento viene memorizzato l'id (chiave), la data e il luogo.
     * 
     * @param idMusicista l'ID del musicista per il quale si vogliono ottenere le convocazioni
     * @return un HashMap contenente le informazioni delle convocazioni, dove la chiave è l'ID dell'evento e il valore è un array di stringhe contenente la data e il luogo dell'evento
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    public static HashMap<Integer, String[]> getConvocazioni(int idMusicista) throws DatabaseException {
        HashMap<Integer, String[]> eventi = null;
        try {
			Connection conn = DBManager.getConnection();
            //Seleziono id, data e luogo di tutti gli eventi a cui il musicista è stato convocato e a cui non ha ancora risposto (esito = IN_ATTESA)
			String query = "SELECT P.IdEvento, E.data, E.luogo FROM Partecipazioni P JOIN Eventi E ON P.idEvento=E.idEvento WHERE P.IdMusicista = ? AND P.esito = 'IN_ATTESA'";
			try {
                eventi = new HashMap<>();
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, idMusicista);
				ResultSet result = stmt.executeQuery();
				while (result.next()) {
                    String[] info = new String[2];
                    info[0] = result.getString(2);      //data
                    info[1] = result.getString(3);      //luogo
                    eventi.put(result.getInt(1), info); //idEvento -> [data, luogo]
                }
			}catch(SQLException e) {
				throw new DatabaseException("Errore lettura nome e cognome del musicista");
			} finally {
				DBManager.closeConnection();
			}
        }catch(SQLException e) {
			throw new DatabaseException("Errore di connessione database");
		}
        return eventi;
    }
    
    /**
     * Inserisce una nuova disponibilità nella data indicata nel database per un musicista specifico.
     * Provare ad inserire una disponibilità precedentemente inserita non darà errore.
     * 
     * @param idMusicista l'ID del musicista
     * @param data la data della disponibilità con formato yyyy-MM-dd
     * @throws DatabaseException se si verifica un errore durante l'inserimento della disponibilità o la connessione al database
     */
    public static void inserisciDisponibilita(int idMusicista, String data) throws DatabaseException{
        String query = "INSERT INTO Disponibility(IdMusicista, Data) VALUES (?, ?)";
        try {
            Connection conn = DBManager.getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, idMusicista);
                ps.setString(2, data);
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                //Blocco vuoto perchè se si prova ad inserire una data precedentemente già inserita non è un errore
                //ma un'operazione che non ha effetti
            } catch (SQLException e) {
                throw new DatabaseException("Errore durante l'inserimento della disponibilità");
            } finally {
                DBManager.closeConnection();
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore di connessione al database");
        } 
    }

    /**
     * Aggiorna l'esito della convocazione di un musicista per un determinato evento nel database.
     * 
     * @param esito il valore booleano che indica se la convocazione è accettata (true) o rifiutata (false)
     * @param idEvento l'ID dell'evento per il quale il musicista è stato convocato
     * @param idMusicista l'ID del musicista convocato
     * @throws DatabaseException se si verifica un errore durante l'aggiornamento dell'esito della convocazione o la connessione al database
     */
    public static void updateEsitoConvocazione (boolean esito, int idEvento, int idMusicista) throws DatabaseException{
        String query = "UPDATE Partecipazioni SET Esito = ? WHERE IdEvento = ? AND IdMusicista = ?";
        try {
            Connection conn = DBManager.getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                if (esito) {
                    ps.setString(1, "ACCETTATO");
                } else {
                    ps.setString(1, "RIFIUTATO");
                }
                ps.setInt(2, idEvento);
                ps.setInt(3, idMusicista);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Errore durante l'aggiornamento dell'esito della convocazione");
            } finally {
                DBManager.closeConnection();
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore di connessione al database");
        } 
    }
}