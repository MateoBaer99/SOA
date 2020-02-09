package org.universite;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@ApplicationPath("/api")
public class VentesApplication extends ResourceConfig{
    
    public VentesApplication () {
        // Register resources and providers using package-scanning.
        packages("org.universite.ventes.api");

        // Register my custom provider - not needed if it's in my.package.
       // register(SecurityRequestFilter.class);
        // Register an instance of LoggingFilter.
    //    register(new LoggingFilter(LOGGER, true));

        // Permet d'activer les traces : voir en-têtes HTTP de la réponse
        property(ServerProperties.TRACING, "ALL");
        property(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED,"true");
    }

	public static void main(String[] args) {
		SpringApplication.run(VentesApplication.class, args);
	}
}