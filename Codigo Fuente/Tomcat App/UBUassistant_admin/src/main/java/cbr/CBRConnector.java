package cbr;


import org.apache.log4j.Logger;

import jcolibri.cbrcore.Connector;
import jcolibri.exception.InitializingException;


/**
 * 
 * @author Daniel Santidrian Alonso
 *
 */
public class CBRConnector{
	
	/**
	 * Global variables
	 */
	Connector connector;
	
	private static final Logger logger = Logger.getLogger(CBRConnector.class);
	
	private static final CBRConnector instance = new CBRConnector();
	
	/**
	 * Method that always return the same instance (Singleton Pattern)
	 * @return instance Instance of the class.
	 */
	public static CBRConnector getInstance(){
		
		return instance;
	}
	
	/**
	 * Constructor of the class
	 */
	private CBRConnector(){
		try {
			getUniqueConnector();
		} catch (InitializingException e) {
			logger.error(e.toString());
		}
	}
	
	
	/**
	 * Method that configures the connector and load the cases from the database.
	 * @throws InitializingException Exception that is thrown when it is not possible to build the connector or the case base.
	 */
	public void getUniqueConnector() throws InitializingException{
		
		
		connector = new jcolibri.connector.DataBaseConnector();
		
		connector.initFromXMLfile(jcolibri.util.FileIO
				.findFile("databaseconfig.xml"));
		
	}
	
	/**
	 * Function that returns the connector.
	 * @return connector Connector to the database.
	 */
	public Connector getConnector(){
		return this.connector;
	}
}