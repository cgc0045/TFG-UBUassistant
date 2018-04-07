package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


/**
 * 
 * @author Daniel Santidrian Alonso
 *
 */
public class DatabaseConnection {
	
	/**
	 * Global variables
	 */
	private Connection con = null;
	private List<String> sentenceList = new ArrayList<>();
	private List<String> saluteList = new ArrayList<>();
	private List<String> saluteResponseList = new ArrayList<>();
	private String userID;
	private DatabaseUtil dbu = new DatabaseUtil();
	
	private static final Logger logger = Logger.getLogger(DatabaseConnection.class);
	
	/**
	 * Constructor of the class that connects the database.
	 * @param userID unique identifier for a user.
	 */
	public DatabaseConnection(String userID) {
		
		this.userID=userID;
		
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
		
		createLists();
	}
	
	/**
	 * Method that reads the tables of the database and creates the sentenceList, 
	 * saluteList and saluteResponseList.
	 */
	private void createLists(){
		
		Statement stmt = null;
		ResultSet rs = null;
		
		//Creation of the list containing the sentences
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM frases");
			while (rs.next()) {
				sentenceList.add(rs.getString("frase"));
			}	
		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			
			dbu.close(stmt);
			dbu.close(rs);
		} 
		
		//Creation of the list containing the salutes
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM saludos");
			while (rs.next()) {
				saluteList.add(rs.getString("saludo"));
				saluteResponseList.add(rs.getString("respuesta"));
			}	
		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			
			dbu.close(stmt);
			dbu.close(rs);
		}
	}
	
	/**
	 * Method that store in a table of the database the words that the users
	 * select when there is not an answer available
	 * @param p1 the word that gets the best similarity of the text input by the user
	 * @param p2 the answer that will be associated to the first word
	 */
	public void learnCases(String p1, String p2){
		
		boolean flag = false;
		String palabra2=" ";
		
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		try {
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM aprendizaje");
			while (rs.next()) {
				if(p1.equals(rs.getString("palabra1"))){
					flag=true;
					palabra2=rs.getString("palabra2");
				}
			}	
			
			if(!flag || !palabra2.equals(p2)){
				
				pst = con.prepareStatement(
						"INSERT INTO aprendizaje (userid, palabra1, palabra2) VALUES (?, ?, ?)");
				pst.setString(1, userID);
				pst.setString(2, p1);
				pst.setString(3, p2);
				
				pst.executeUpdate();
			}

		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(stmt);
			dbu.close(rs);
			dbu.close(pst);
		}
	}
	
	
	/**
	 * Method that increases the field numBusquedas in the database
	 * @param palabras the words that are searched
	 * @param respuesta response for that set of words
	 */
	public void aumentarNumBusquedas(LinkedHashSet<String> palabras, String respuesta){
		
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		boolean flag=false;
		int palabraId = 0;
		int numbusquedas=0;
		String categoria=null;
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		List<String> temp = new ArrayList<>();
		
		temp.addAll(palabras);
		Collections.sort(temp);
		
		try{
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM logger");
			while (rs.next()) {
				
				List<String> databaseWords = createDatabaseWords(rs);
				
				if(databaseWords.equals(temp) && userID.equals(rs.getString("userid"))){

						flag=true;
						palabraId=rs.getInt("id");
						numbusquedas=rs.getInt("num_busquedas");
				}
			}	
			
			if(flag){
				
				pst = con.prepareStatement("UPDATE logger SET num_busquedas = ?, fecha=? WHERE id=?");
				numbusquedas+=1;
				pst.setInt(1, numbusquedas);
				pst.setString(2, sdf.format(new Date()));
				pst.setInt(3, palabraId);
				pst.executeUpdate();
				
			}else{
				
				categoria=getCategoria(respuesta);
				
				pst = con.prepareStatement("INSERT INTO logger "
												+ "(keyWord1, keyWord2, keyWord3, keyWord4, keyWord5, categoria, num_busquedas, num_votos, valoracion_total,"
												+ " fecha, respuesta, userid) "
												+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
				
				for(int i=0; i<temp.size(); i++){
					pst.setString(i+1, temp.get(i));
				}
				
				for(int i=temp.size()+1;i<6;i++)
					pst.setNull(i, java.sql.Types.VARCHAR);
				
				pst.setString(6, categoria);
				pst.setInt(7, 1);
				pst.setInt(8, 0);
				pst.setInt(9, 0);
				pst.setString(10, sdf.format(new Date()));
				pst.setString(11, respuesta);
				pst.setString(12, userID);
				pst.executeUpdate();
			}	
			
		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(stmt);
			dbu.close(rs);
			dbu.close(pst);
		}
	}

	/**
	 * Function that create a list with the keyWords in database and delete nulls.
	 * @param rs Resultset.
	 * @return databaseWords list of the database words.
	 * @throws SQLException Exception that is thrown when the is a problem with SQL.
	 */
	private List<String> createDatabaseWords(ResultSet rs) throws SQLException {
		List<String> databaseWords = new ArrayList<>();
		databaseWords.add(rs.getString("keyWord1"));
		databaseWords.add(rs.getString("keyWord2"));
		databaseWords.add(rs.getString("keyWord3"));
		databaseWords.add(rs.getString("keyWord4"));
		databaseWords.add(rs.getString("keyWord5"));
		
		databaseWords.removeAll(Collections.singleton(null));
		Collections.sort(databaseWords);
		return databaseWords;
	}
	
	/**
	 * Method that saves the rating of the user to a specific answer into the database
	 * @param palabras the words that are searched
	 * @param vote the rating to the answer for the words
	 */
	public void saveVote(LinkedHashSet<String> palabras,int vote){
		
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		int palabraId = 0;
		int numvotos=0;
		int valoraciontotal=0;
		
		List<String> temp = new ArrayList<>();
		
		temp.addAll(palabras);
		Collections.sort(temp);
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM logger");
			while (rs.next()) {
				
				List<String> databaseWords = createDatabaseWords(rs);
				
				if(databaseWords.equals(temp) && userID.equals(rs.getString("userid"))){

					palabraId=rs.getInt("id");
					numvotos=rs.getInt("num_votos");
					valoraciontotal=rs.getInt("valoracion_total");
				}
			}
			
			pst = con.prepareStatement("UPDATE logger SET num_votos=?, valoracion_total=? WHERE id=?");
			numvotos+=1;
			pst.setInt(1, numvotos);
			valoraciontotal+=vote;
			pst.setInt(2, valoraciontotal);
			pst.setInt(3, palabraId);
			pst.executeUpdate();
					
		} catch (SQLException e) {
			logger.error(e.toString());
		} finally{
			dbu.close(stmt);
			dbu.close(rs);
			dbu.close(pst);
		}
	}
	
	/**
	 * Function that returns the category of a response.
	 * @param respuesta response from which we want to know its category.
	 * @return categoria category of the response.
	 */
	private String getCategoria(String respuesta){

		return dbu.getCategoria(con, respuesta);
	}

	
	/**
	 * Function that returns the sentenceList
	 * @return sentenceList List of the sentences
	 */
	public List<String> getSentenceList(){
		return sentenceList;
	}
	
	/**
	 * Function that returns the saluteList
	 * @return saluteList List of salutes
	 */
	public List<String> getSaluteList(){
		return saluteList;
	}
	
	/**
	 * Function that returns the saluteResponseList
	 * @return saluteResponseList List of responses to salutes
	 */
	public List<String> getSaluteResponseList(){
		return saluteResponseList;
	}
	
}
