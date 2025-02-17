package ca.ulaval.glo4002.application.infrastructure.initialization;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import ca.ulaval.glo4002.application.infrastructure.sqLite.config.SQLiteConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class PersistenceInitializer {
    private static final Logger logger = LoggerFactory.getLogger(PersistenceInitializer.class);

    private static final String PERSISTENCE_PROPERTY = "persistence";
    private static final String SQLITE_PERSISTENCE = "sqlite";
    private static final String DEFAULT_PERSISTENCE = "memory";

    private static FestivalRepository festivalRepository;
    private static FestivalRepositoryFactory festivalRepositoryFactory;
    private static Festival festivalInstance;

    public static synchronized Festival initialize() throws SQLException {
        String persistenceType = System.getProperty(PERSISTENCE_PROPERTY, DEFAULT_PERSISTENCE);

        if (festivalInstance != null) {
            return festivalInstance;
        }

        logger.info("Initializing persistence...");

        if (festivalRepositoryFactory == null) {
            festivalRepositoryFactory = new FestivalRepositoryFactory();
        }

        festivalRepository = festivalRepositoryFactory.createFestivalRepository(persistenceType);
        initializeFestival(persistenceType);

        if (festivalInstance == null) {
            throw new IllegalStateException("Festival initialization failed. Festival instance is null.");
        }

        return festivalInstance;
    }

    private static void initializeFestival(String persistenceType) throws SQLException {
        FestivalInitializer festivalInitializer;
        if (persistenceType.equals(SQLITE_PERSISTENCE)) {
            festivalInitializer = new FestivalInitializer(festivalRepository, SQLiteConnection.getConnection());
        }
        else {
            festivalInitializer = new FestivalInitializer(festivalRepository, null);
        }
        festivalInitializer.initialize();
        festivalInstance = festivalRepository.getFestival();
    }

    public static FestivalRepository getFestivalRepository() {
        if (festivalRepository == null) {
            throw new IllegalStateException("FestivalRepository is not initialized. Call initialize() first.");
        }
        return festivalRepository;
    }
}
