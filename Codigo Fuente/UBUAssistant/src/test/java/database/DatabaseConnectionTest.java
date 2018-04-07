package database;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Daniel Santidrian Alonso
 *
 */
public class DatabaseConnectionTest extends AbstractTestCase{
	
	DatabaseConnection db;
	String userID;
	
	private static final Logger logger = Logger.getLogger(DatabaseConnectionTest.class);
	
	private static final String INVENTADA="inventada";
	private static final String RESPUESTAINVENTADA="respuesta inventada";
	private static final String INVENTADA1="inventada1";
	private static final String INVENTADA2="inventada2";
	
	private DatabaseUtil dbu = new DatabaseUtil();
	
	@Before
    @Override
    public void setUp() {
        super.setUp();
        DateFormat formatForId = new SimpleDateFormat("yyMMddHHmmssSSS");
    	userID=formatForId.format(new Date()); 
    	
    	db = new DatabaseConnection(userID);
    }
	
	

	@Test
	public void getListsTest() {
		
		List<String> salutes = new ArrayList<>();
		
		salutes.add("Hola");
		salutes.add("Buenos dias");
		salutes.add("Buenas tardes");
		salutes.add("Buenas noches");
		salutes.add("Buenas");
		salutes.add("Adios");
		salutes.add("Hasta luego");
		salutes.add("Ciao");
		salutes.add("Eres una pesada");
		salutes.add("Callate");
	
		List<String> responses = new ArrayList<>();

		responses.add("Hola, estoy preparada para responder, adelánte");
		responses.add("Buenos días, ponme a prueba con tus preguntas");
		responses.add("Buenas tardes, ¿tienes alguna duda? No dudes en preguntarme");
		responses.add("Buenas noches, ¿qué te apetece preguntar?");
		responses.add("Muy buenas serán si te contesto correctamente");
		responses.add("¡Adiós!, espero haberte servido de ayuda");
		responses.add("Hasta luego, espero que volvamos a vernos");
		responses.add("¡Arrivederci!");
		responses.add("No te pongas así, seguro que podemos tener una conversación productiva");
		responses.add("Lo siento, solo intentaba ayudar");
		
		assertTrue(db.getSaluteList().containsAll(salutes));
		assertTrue(db.getSaluteResponseList().containsAll(responses));
		
		List<String> sentences = new ArrayList<>();
		
		sentences.add("Hola soy UBUassistant, ¿en qué puedo ayudarte?");
		sentences.add("Muy buenas, me llamo UBUassistant y estoy lista para ayudarte ¡adelante!");
		sentences.add("Hola, si necesitas ayuda aquí me tienes, aquí tienes a UBUassistant");
		sentences.add("Bienvenido, ante cualquier duda de tu universidad UBUassistant al rescate");
		sentences.add("UBUassistant te da la bienvenida, ¿Empezamos?, pregunta cualquier duda sobre la UBU");
		sentences.add("Tal vez esto te ayude");
		sentences.add("Prueba con la siguiente información");
		sentences.add("Espero que esto te ayude");
		sentences.add("Esto es lo que he encontrado al respecto");
		
		
		assertTrue(db.getSentenceList().containsAll(sentences));

	}
	
	@Test
	public void learnTest() {
		
		db.learnCases(INVENTADA,RESPUESTAINVENTADA);
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			String palabra2="";
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM aprendizaje");
			
			while (rs.next()) {
				if(rs.getString("palabra1").equals(INVENTADA)){
					palabra2=rs.getString("palabra2");
				}
			}	
			
			assertEquals(palabra2, RESPUESTAINVENTADA);
			
			stmt.executeUpdate("DELETE FROM aprendizaje WHERE palabra1='inventada'");

		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(rs);
			dbu.close(stmt);
		}
	}
	
	@Test
	public void learnMultipleTest() {
		
		db.learnCases(INVENTADA,"respuesta inventada1");
		db.learnCases(INVENTADA,"respuesta inventada2");
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			List<String> palabra2= new ArrayList<>();
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM aprendizaje");
			
			while (rs.next()) {
				if(rs.getString("palabra1").equals(INVENTADA)){
					palabra2.add(rs.getString("palabra2"));
				}
			}	
			
			List<String> temp = new ArrayList<>();
			temp.add("respuesta inventada1");
			temp.add("respuesta inventada2");
			
			assertTrue(palabra2.containsAll(temp));
			
			stmt.executeUpdate("DELETE FROM aprendizaje WHERE palabra1='inventada'");

		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(rs);
			dbu.close(stmt);
		}
	}
	
	
	@Test
	public void aumentarNumBusquedaTest() {
		
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedHashSet<String> temp = new LinkedHashSet<>();
		LinkedHashSet<String> temp2 = new LinkedHashSet<>();
		
		increaseSearchNum(temp, temp2);
		
		try{
			
			List<Integer> num = new ArrayList<>();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM logger");
			
			while (rs.next()) {
				if(rs.getString("keyWord1").equals(INVENTADA1)){
					num.add(rs.getInt("num_busquedas"));
				}
			}	
			

			assertSame(num.get(0),1);
			assertSame(num.get(1),2);
			
			stmt.executeUpdate("DELETE FROM logger WHERE keyWord1='inventada1'");

		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(rs);
			dbu.close(stmt);
		}
	}
	

	@Test
	public void saveVoteTest() {
		
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedHashSet<String> temp = new LinkedHashSet<>();
		LinkedHashSet<String> temp2 = new LinkedHashSet<>();
		increaseSearchNum(temp, temp2);
		
		db.saveVote(temp, 5);
		db.saveVote(temp2, 4);
		db.saveVote(temp2, 5);
		
		try{
			
			List<Integer> num = new ArrayList<>();
			List<Integer> vot = new ArrayList<>();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM logger");
			
			while (rs.next()) {
				if(rs.getString("keyWord1").equals(INVENTADA1)){
					num.add(rs.getInt("num_votos"));
					vot.add(rs.getInt("valoracion_total"));
				}
			}	
			

			assertSame(num.get(0),1);
			assertSame(num.get(1),2);
			
			stmt.executeUpdate("DELETE FROM logger WHERE keyWord1='inventada1'");

		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(rs);
			dbu.close(stmt);
		}
	}



	private void increaseSearchNum(LinkedHashSet<String> temp, LinkedHashSet<String> temp2) {
		temp.add(INVENTADA1);
		temp.add(INVENTADA2);
		temp.add("inventada3");
		temp2.add(INVENTADA1);
		temp2.add(INVENTADA2);
		
		db.aumentarNumBusquedas(temp, RESPUESTAINVENTADA);
		db.aumentarNumBusquedas(temp2, RESPUESTAINVENTADA);
		db.aumentarNumBusquedas(temp2, RESPUESTAINVENTADA);
	}

}
