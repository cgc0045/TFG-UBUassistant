package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * 
 * @author Daniel Santidrian Alonso
 *
 */
public class DatabaseUtil {
	
	/**
	 * Global variable
	 */
	private static final Logger logger = Logger.getLogger(DatabaseUtil.class);
	

	/**
	 * Function that returns the category of a response.
	 * @param con Connection to the database
	 * @param respuesta response from which we want to know its category.
	 * @return categoria category of the response.
	 */
	public String getCategoria(Connection con, String respuesta){
		
		int id = 0;
		String categoria = null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		
		try{
			pst = con.prepareStatement("SELECT * FROM casesolution WHERE answer=?");
			pst.setString(1, respuesta);
			rs = pst.executeQuery();
			while (rs.next()) {
				id=rs.getInt("id");
			}	
			
			pst1 = con.prepareStatement("SELECT * FROM casedescription WHERE id=?");
			pst1.setInt(1, id);
			rs1 = pst1.executeQuery();
			while (rs1.next()) {
				categoria=rs1.getString("categoria");
			}	
			
			
		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			close(pst);
			close(pst1);
			close(rs);
			close(rs1);
		}
		
		return categoria;
		
	}
	
	/**
	 * Method that closes a statement received
	 * @param statement statement received
	 */
	public void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {				
				logger.error(e.toString());
			}
		}
	}
	
	/**
	 * Method that closes a result set received
	 * @param rs result set received
	 */
	public void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {				
				logger.error(e.toString());
			}
		}
	}

}
