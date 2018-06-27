package UBUassistant.app;

import org.glassfish.jersey.server.ResourceConfig;
/**
 * 
 * @author Carlos González Calatrava
 *
 */
public class AppConfig extends ResourceConfig {

	/**
	 * Class constructor
	 */
	public AppConfig() {
		packages("UBUassistant.service");
	}
}
