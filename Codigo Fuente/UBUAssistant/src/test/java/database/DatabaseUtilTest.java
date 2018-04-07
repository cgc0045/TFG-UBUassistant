package database;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Daniel Santidrian Alonso
 *
 */
public class DatabaseUtilTest extends AbstractTestCase{
	
	DatabaseAdministration db;
	
	@Before
    @Override
    public void setUp() {
        super.setUp();
        db = new DatabaseAdministration();
    }

	
	@Test
	public void getCategoriaTest() {
		
		DatabaseUtil dbu = new DatabaseUtil();
		assertEquals("universidad", dbu.getCategoria(con, "http://www3.ubu.es/ubuespacio/"));
		assertEquals("internacional", dbu.getCategoria(con, "http://www.ubu.es/servicio-de-relaciones-internacionales"));
	}

}
