package database;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Daniel Santidrian Alonso
 *
 */
public class DatabaseAdministrationTest extends AbstractTestCase{
	
	DatabaseAdministration db;

	private static final Logger logger = Logger.getLogger(DatabaseAdministrationTest.class);
	
	private DatabaseUtil dbu = new DatabaseUtil();
	
	private static final String KEYWORD1 = "keyWord1";
	private static final String TOTAL = "total";
	private static final String USERID = "01111111";
	private static final String PALABRA1 = "palabra1";
	private static final String PALABRA2 = "palabra2";
	
	@Before
    @Override
    public void setUp() {
        super.setUp();
        db = new DatabaseAdministration();
    }

	
	@Test
	public void addCaseTest() {
		
		String cat="";
		
		List<String> lista = new ArrayList<>();
		
		lista.add("CasoInventado1");
		
		db.addCase(lista, "categoriaInventada1", "respInventada1");
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM casedescription WHERE keyWord1='CasoInventado1'");
			
			while (rs.next()) {
				if(rs.getString(KEYWORD1).equals("CasoInventado1")){
					cat=rs.getString("categoria");
				}
			}	
			
			assertEquals(cat, "categoriaInventada1");
			
			stmt.executeUpdate("DELETE FROM casedescription WHERE keyWord1='CasoInventado1'");
			stmt.executeUpdate("DELETE FROM casesolution WHERE answer='respInventada1'");

		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(rs);
			dbu.close(stmt);
		}
	}
	
	
	@Test
	public void editCaseTest() {
		
		List<String> lista = new ArrayList<>();
		
		lista.add("CasoInventado2");
		
		db.addCase(lista, "categoriaInventada2", "respInventada2");
		
		Statement stmt = null;
		ResultSet rs = null;
		Statement stmt2 = null;
		ResultSet rs2 = null;
		Statement stmt3 = null;
		ResultSet rs3 = null;
		
		try{
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery("SELECT id FROM casedescription where categoria='categoriaInventada2'");
			
			rs.next();
			int id=rs.getInt("id");
			
			String[] lista2 = new String[5];
			lista2[0]="editCase";
			
			db.editCase(String.valueOf(id), lista2, "editCat", "editAnswer");
			
			stmt2 = con.createStatement();
			rs2 = stmt2.executeQuery("SELECT * FROM casedescription WHERE id="+id);
			
			stmt3 = con.createStatement();
			rs3 = stmt3.executeQuery("SELECT * FROM casesolution WHERE id="+id);
			
			String resp="";
			String cat="";
			
			while (rs2.next() && rs3.next()) {
				cat=rs2.getString("categoria");
				resp=rs3.getString("answer");
			}	
			
			
			assertEquals(cat, "editCat");
			assertEquals(resp, "editAnswer");
			
			stmt.executeUpdate("DELETE FROM casedescription WHERE categoria='editCat'");
			stmt.executeUpdate("DELETE FROM casesolution WHERE answer='editAnswer'");

		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(rs);
			dbu.close(stmt);
			dbu.close(rs2);
			dbu.close(stmt2);
			dbu.close(rs3);
			dbu.close(stmt3);
		}
	}
	
	@Test
	public void removeCaseTest() {
		
		List<String> lista = new ArrayList<>();
		
		lista.add("CasoInventado3");
		
		db.addCase(lista, "categoriaInventada3", "respInventada3");
		
		Statement stmt = null;
		ResultSet rs = null;
		Statement stmt3 = null;
		ResultSet rs3 = null;
		
		try{
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery("SELECT id FROM casedescription where categoria='categoriaInventada3'");
			
			rs.next();
			int id=rs.getInt("id");
			
			String[] lista2 = new String[5];
			lista2[0]="editCase";
			
			db.removeCase(String.valueOf(id));
			
			stmt3 = con.createStatement();
			rs3 = stmt3.executeQuery("SELECT COUNT(*) AS total FROM casedescription WHERE categoria='categoriaInventada3'");
			
			rs3.next();
			int num=rs3.getInt(TOTAL);
			
			
			assertTrue(num==0);
			
			stmt.executeUpdate("DELETE FROM casedescription WHERE categoria='categoriaInventada3'");
			stmt.executeUpdate("DELETE FROM casesolution WHERE answer='respInventada3'");

		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(rs);
			dbu.close(stmt);
			dbu.close(rs3);
			dbu.close(stmt3);
		}
	}
	

	@Test
	public void executeLearnTest() {
		
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;

		
		try{
			
			pst = con.prepareStatement("INSERT INTO aprendizaje "
										+ "(userid,palabra1,palabra2) VALUES (?,?,?)");
		
			pst.setString(1, USERID);
			pst.setString(2, PALABRA1);
			pst.setString(3, PALABRA2);
			
			pst.executeUpdate();
			
			db.executeLearn(PALABRA1, PALABRA2);
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM casedescription");
			
			String p="";
			
			while(rs.next()){
				if(rs.getString(KEYWORD1).equals(PALABRA1)){
					p = rs.getString(KEYWORD1);
				}
			}
			
			assertEquals(p, PALABRA1);
			
			stmt.executeUpdate("DELETE FROM casedescription WHERE keyWord1='palabra1'");
			stmt.executeUpdate("DELETE FROM casesolution WHERE answer='palabra2'");
			

		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(rs);
			dbu.close(stmt);
			dbu.close(pst);
		}
	}
	
	
	@Test
	public void ignoreLearnTest() {
		
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		try{
			
			pst = con.prepareStatement("INSERT INTO aprendizaje "
										+ "(userid,palabra1,palabra2) VALUES (?,?,?)");
		
			pst.setString(1, USERID);
			pst.setString(2, PALABRA1);
			pst.setString(3, PALABRA2);
			
			pst.executeUpdate();
			
			db.ignoreLearn(PALABRA1, PALABRA2);
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM aprendizaje WHERE palabra1='palabra1'");
			
			rs.next();
			int count=rs.getInt(TOTAL);
			
			assertTrue(count==0);
			

		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(rs);
			dbu.close(stmt);
			dbu.close(pst);
		}
	}
	
	
	@Test
	public void clearLogTest() {
		
		Statement stmt = null;
		ResultSet rs = null;
		Statement stmt2 = null;
		ResultSet rs2 = null;
		PreparedStatement pst = null;
		
		try{
			
			pst = con.prepareStatement("INSERT INTO logger "
						+ "(userid,fecha,keyWord1,keyWord2,keyWord3,keyWord4,keyWord5,categoria,respuesta,"+
						"num_busquedas,num_votos,valoracion_total) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
		
			pst.setString(1, USERID);
			pst.setString(2, "2017-06-14 11:41:55");
			pst.setString(3, PALABRA1);
			pst.setString(4, PALABRA2);
			pst.setString(5, "palabra3");
			pst.setString(6, "palabra4");
			pst.setString(7, "palabra5");
			pst.setString(8, "cat");
			pst.setString(9, "resp");
			pst.setInt(10, 2);
			pst.setInt(11, 3);
			pst.setInt(12, 4);
			
			pst.executeUpdate();
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM logger");
			
			rs.next();
			int count1=rs.getInt(TOTAL);
			assertTrue(count1!=0);
			
			db.clearLog();
			
			stmt2 = con.createStatement();
			rs2 = stmt2.executeQuery("SELECT COUNT(*) AS total1 FROM logger");
			
			rs2.next();
			int count2=rs2.getInt("total1");
			assertTrue(count2==0);
			

		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(rs);
			dbu.close(stmt);
			dbu.close(rs2);
			dbu.close(stmt2);
			dbu.close(pst);
		}
	}

}
