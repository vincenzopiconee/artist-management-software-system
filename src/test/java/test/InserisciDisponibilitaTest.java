package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import control.GestioneEventi;
import database.DBManager;
import exception.ControllerException;

public class InserisciDisponibilitaTest {

	@Before
	public void setUp() throws Exception {
		Connection conn = DBManager.getConnection();
		String query = "DELETE FROM Disponibility";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.executeUpdate();
		System.out.println("INIZIO TEST");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("FINE TEST");
	}

	@Test
	public void testInserisciDisponibilita() {
		GestioneEventi ge = GestioneEventi.getInstance();
		try {
			ge.inserisciDisponibilita(1, "2027-01-02", "2027-01-02");
			
			Connection conn = DBManager.getConnection();
			String query = "SELECT DATA FROM DISPONIBILITY WHERE IDMUSICISTA = 1";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				assertEquals("2027-01-02", rs.getString(1));
			} else {
				fail("Disponibilita non inserita");
			}
		} catch (ControllerException | SQLException e) {
			e.printStackTrace();
			fail("Eccezione non prevista");
		}
	}
	
	@Test(expected = ControllerException.class)
	public void testInserisciDisponibilitaIdNonValido() throws ControllerException  {
		GestioneEventi ge = GestioneEventi.getInstance();
		try {
			ge.inserisciDisponibilita(-1, "2027-01-02", "2027-01-03");
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ControllerException.class)
	public void testInserisciDisponibilitaDataPassata() throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		try {
			ge.inserisciDisponibilita(1, "2022-01-02", "2027-01-03");
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ControllerException.class)
	public void testInserisciDisponibilitaDataInizioNonFormattata() throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		try {
			ge.inserisciDisponibilita(1, "2027-01as-uujt", "2027-01-03");
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ControllerException.class)
	public void testInserisciDisponibilitaDataFineNonFormattata() throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		try {
			ge.inserisciDisponibilita(1, "2027-01-02", "20ds-01-03");
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ControllerException.class)
	public void testInserisciDisponibilitaDataFinePrecDataInizio() throws ControllerException{
		GestioneEventi ge = GestioneEventi.getInstance();
		try {
			ge.inserisciDisponibilita(1, "2027-01-02", "2025-01-03");
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
}
