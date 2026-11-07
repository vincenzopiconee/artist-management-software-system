package test;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import control.GestioneEventi;
import database.DBManager;
import exception.ControllerException;

public class GestisciConvocazioniTest {
	
	@Before
	public void setUp() throws Exception {
		Connection conn = DBManager.getConnection();
		String query = "DELETE FROM Eventi";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.executeUpdate();

		query = "INSERT INTO EVENTI (IDEVENTO, DATA, LUOGO, TIPOLOGIA, COSTOTOTALE, MUSICISTIINCERTI) VALUES (1, '2025-01-01', 'STADIO DAM', 'STRUMENTALE', 5, 1)";
		stmt = conn.prepareStatement(query);
		stmt.executeUpdate();

		query = "INSERT INTO PARTECIPAZIONI (IDEVENTO, IDMUSICISTA) VALUES (1, 1)";
		stmt = conn.prepareStatement(query);
		stmt.executeUpdate();
		System.out.println("INIZIO TEST");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("FINE TEST");
	}

	@Test
	public void testGestisciConvocazioneEsitoTrue() {
		GestioneEventi ge = GestioneEventi.getInstance();
		try {
			ge.gestisciConvocazione(1, 1, true);	
			
			Connection conn = DBManager.getConnection();
			String query = "SELECT ESITO FROM PARTECIPAZIONI WHERE IDEVENTO = 1 AND IDMUSICISTA = 1";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				assertEquals("ACCETTATO", rs.getString(1));
			} else {
				fail("Prenotazione non effettuata");
			}
			
			query = "SELECT STATO FROM EVENTI WHERE IDEVENTO = 1";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			if (rs.next()) {
				assertEquals("CONFERMATO", rs.getString(1));
			} else {
				fail("Evento non confermato");
			}
		} catch (ControllerException | SQLException e) {
			e.printStackTrace();
			fail("Eccezione non prevista");
		}
	}
	
	@Test
	public void testGestisciConvocazioniEsitoFalse() {
		GestioneEventi ge = GestioneEventi.getInstance();
		try {
			ge.gestisciConvocazione(1, 1, false);
			
			Connection conn = DBManager.getConnection();
			String query = "SELECT ESITO FROM PARTECIPAZIONI WHERE IDEVENTO = 1 AND IDMUSICISTA = 1";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				assertEquals("RIFIUTATO", rs.getString(1));
			} else {
				fail("Prenotazione non effettuata");
			}
		} catch (ControllerException | SQLException e) {
			e.printStackTrace();
			fail("Eccezione non prevista");
		}
	}
	
	@Test(expected = ControllerException.class)
	public void testGestisciConvocazioniMusicistaNonValido() throws ControllerException {
		GestioneEventi ge = GestioneEventi.getInstance();
		try {
			ge.gestisciConvocazione(-1, 1, true);
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ControllerException.class)
	public void testGestisciConvocazioniEventoNonValido() throws ControllerException {
		GestioneEventi ge = GestioneEventi.getInstance();
		try {
			ge.gestisciConvocazione(1, -1, true);
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
}
