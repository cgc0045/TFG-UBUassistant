package UBUassistant.app;

import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {

	public AppConfig() {
		packages("UBUassistant.service");
	}
}
