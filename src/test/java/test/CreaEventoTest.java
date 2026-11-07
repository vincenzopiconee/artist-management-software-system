package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import control.GestioneEventi;
import database.DBManager;
import exception.ControllerException;

public class CreaEventoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		Connection conn = DBManager.getConnection();
		String query = "DELETE FROM Disponibility";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.executeUpdate();

		query = "INSERT INTO DISPONIBILITY (IDMUSICISTA, DATA) VALUES (1, '2027-01-01')";
		stmt = conn.prepareStatement(query);
		stmt.executeUpdate();

		query = "INSERT INTO DISPONIBILITY (IDMUSICISTA, DATA) VALUES (2, '2027-01-01')";
		stmt = conn.prepareStatement(query);
		stmt.executeUpdate();

		query = "INSERT INTO DISPONIBILITY (IDMUSICISTA, DATA) VALUES (3, '2027-01-01')";
		stmt = conn.prepareStatement(query);
		stmt.executeUpdate();
	}

	@Before
	public void setUp() throws Exception {
		Connection conn = DBManager.getConnection();
		String query = "DELETE FROM Eventi";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.executeUpdate();
		System.out.println("INIZIO TEST");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println(	"FINE TEST");
	}

	@Test
	public void testCreaEvento() {
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "2027-01-01";
		String luogo = "Bar Santos";
		String tipologia = "strumentale";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		musicistiSelezionati.add(1);
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;
		
		try {
			elencoMusicisti = ge.getMusicistiDisponibili(data);
			assertEquals(3, elencoMusicisti.size());
			costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
			assertEquals(5, costoTotale, 0.001);			
		} catch (ControllerException e) {
			e.printStackTrace();
			fail("Eccezione non prevista");			
		}
	}
	
	@Test(expected = ControllerException.class)
	public void testCreaEventoDataPassata()  throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "2020-01-01";
		String luogo = "Bar Santos";
		String tipologia = "strumentale";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		musicistiSelezionati.add(1);
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;

		try {
		elencoMusicisti = ge.getMusicistiDisponibili(data);
		costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test(expected = ControllerException.class)
	public void testCreaEventoDataFormatoNonValido()  throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "llll-kdkw-01";
		String luogo = "Bar Santos";
		String tipologia = "strumentale";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		musicistiSelezionati.add(1);
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;

		try {
			elencoMusicisti = ge.getMusicistiDisponibili(data);
			costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test(expected = ControllerException.class)
	public void testCreaEventoDataLuogoTroppoLungo()  throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "2027-01-01";
		String luogo = "Bar SantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadadSantosdaldnadadad";
		String tipologia = "strumentale";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		musicistiSelezionati.add(1);
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;

		try {
			elencoMusicisti = ge.getMusicistiDisponibili(data);
			costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test
	public void testCreaEventoConcerto(){
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "2027-01-01";
		String luogo = "Bar Santos";
		String tipologia = "concerto";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		musicistiSelezionati.add(1);
		musicistiSelezionati.add(2);
		musicistiSelezionati.add(3);
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;

		try {
			elencoMusicisti = ge.getMusicistiDisponibili(data);
			assertEquals(3, elencoMusicisti.size());
			costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
			assertEquals(65, costoTotale, 0.001);
		} catch (ControllerException e) {
			fail("Eccezione non prevista");

		}
		
	}

	@Test
	public void testCreaEventoEsibizione(){
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "2027-01-01";
		String luogo = "Bar Santos";
		String tipologia = "esibizione";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		musicistiSelezionati.add(1);
		musicistiSelezionati.add(3);
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;

		try {
			elencoMusicisti = ge.getMusicistiDisponibili(data);
			assertEquals(3, elencoMusicisti.size());
			costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
			assertEquals(8, costoTotale, 0.001);
		} catch (ControllerException e) {
			fail("Eccezione non prevista");
		}
		
	}

	@Test
	public void testCreaEventoStrumentale(){
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "2027-01-01";
		String luogo = "Bar Santos";
		String tipologia = "strumentale";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		musicistiSelezionati.add(1);
		musicistiSelezionati.add(2);
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;

		try {
			elencoMusicisti = ge.getMusicistiDisponibili(data);
			assertEquals(3, elencoMusicisti.size());
			costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
			assertEquals(62, costoTotale, 0.001);
		} catch (ControllerException e) {
			fail("Eccezione non prevista");
		}
		
	}

	@Test(expected = ControllerException.class)
	public void testCreaEventoTipologiaSbagliata()  throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "2027-01-01";
		String luogo = "Bar Santos";
		String tipologia = "tipologia";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		musicistiSelezionati.add(1);
		musicistiSelezionati.add(3);
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;

		try {
			elencoMusicisti = ge.getMusicistiDisponibili(data);
			costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test(expected = ControllerException.class)
	public void testCreaEventoNessunMusicistaSelezionato()  throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "2027-01-01";
		String luogo = "Bar Santos";
		String tipologia = "strumentale";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;

		try {
			elencoMusicisti = ge.getMusicistiDisponibili(data);
			costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test(expected = ControllerException.class)
	public void testCreaEventoMusicistiNonValidi()  throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "2027-01-01";
		String luogo = "Bar Santos";
		String tipologia = "strumentale";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		musicistiSelezionati.add(4);
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;
		
		try {
			elencoMusicisti = ge.getMusicistiDisponibili(data);
			costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test(expected = ControllerException.class)
	public void testCreaEventoVincoloConcertoNonRispettato()  throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "2027-01-01";
		String luogo = "Bar Santos";
		String tipologia = "concerto";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		musicistiSelezionati.add(1);
		musicistiSelezionati.add(2);
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;

		try {
			elencoMusicisti = ge.getMusicistiDisponibili(data);
			costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test(expected = ControllerException.class)
	public void testCreaEventoVincoloEsibizioneNonRispettato()  throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "2027-01-01";
		String luogo = "Bar Santos";
		String tipologia = "esibizione";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		musicistiSelezionati.add(1);
		musicistiSelezionati.add(2);
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;

		try {
			elencoMusicisti = ge.getMusicistiDisponibili(data);
			costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test(expected = ControllerException.class)
	public void testCreaEventoVincoloStrumentaleNonRispettato()  throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		String data = "2027-01-01";
		String luogo = "Bar Santos";
		String tipologia = "strumentale";
		List<Integer> musicistiSelezionati = new ArrayList<>();
		musicistiSelezionati.add(3);
		Map<String, Integer> elencoMusicisti = null;
		float costoTotale;

		try {
			elencoMusicisti = ge.getMusicistiDisponibili(data);
			costoTotale = ge.creaEvento(data, luogo, tipologia, musicistiSelezionati);
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
}
