package org.universite;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@ApplicationPath("/api")
public class VentesApplication extends ResourceConfig {
  
   private static final Logger LOGGING_INTERCEPTOR = Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME);
   // Taille maximale du message à logger (tronqué si requête ou réponse plus volumineuse)
   private static final int MAX_ENTITY_SIZE=8192;
   
    public VentesApplication () {
        // Scanne l'ensemble des classes du package à la recherche de ressources Jax-rs (@Path)
        packages("org.universite.ventes.api");

        // Register my custom provider - not needed if it's in my.package.
       // register(SecurityRequestFilter.class);
       
        // Activation des logs (traces des requêtes et réponses
        register(new LoggingFeature(LOGGING_INTERCEPTOR, Level.INFO,LoggingFeature.Verbosity.PAYLOAD_TEXT,MAX_ENTITY_SIZE));

        
        // Permet d'activer les traces : voir en-têtes HTTP de la réponse
        property(ServerProperties.TRACING, "ALL");

    }

	public static void main(String[] args) {
		SpringApplication.run(VentesApplication.class, args);
	}
}