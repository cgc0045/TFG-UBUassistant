package database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Before;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * 
 * @author Daniel Santidrian Alonso
 *
 */
public abstract class AbstractTestCase {
	
	Connection con;
	
	private static final Logger logger = Logger.getLogger(AbstractTestCase.class);

	@Before
    public void setUp() {
		
		MysqlDataSource ds = new MysqlDataSource();

		ds.setUser("root");
		ds.setPassword("1234");
		ds.setDatabaseName("ubuassistant");
		ds.setURL("jdbc:mysql://localhost/ubuassistant");

		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			logger.error("Error al conectar con la base de datos.");
		}
    }
	
}
