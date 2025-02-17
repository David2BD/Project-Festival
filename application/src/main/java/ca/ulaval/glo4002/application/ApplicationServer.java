package ca.ulaval.glo4002.application;

import ca.ulaval.glo4002.application.infrastructure.initialization.PersistenceInitializer;
import ca.ulaval.glo4002.application.interfaces.configuration.RestServerConfiguration;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ApplicationServer implements Runnable {
    private static final int PORT = 8181;
    private final Logger logger = LoggerFactory.getLogger(ApplicationServer.class);

    public static void main(String[] args) {
        new ApplicationServer().run();
    }

    public void run() {
        try {
            PersistenceInitializer.initialize();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler("/");
        server.setHandler(contextHandler);
        ResourceConfig packageConfig = new ResourceConfig().packages("ca.ulaval.glo4002.application")
                .register(JacksonFeature.withoutExceptionMappers()).register(RestServerConfiguration.class);

        ServletContainer container = new ServletContainer(packageConfig);
        ServletHolder servletHolder = new ServletHolder(container);

        contextHandler.addServlet(servletHolder, "/*");

        try {
            server.start();
            logger.info("Server started on port " + PORT);
            server.join();
        }
        catch (Exception e) {
            logger.error("Error starting server", e);
        } finally {
            if (server.isRunning()) {
                server.destroy();
            }
        }
    }
}
