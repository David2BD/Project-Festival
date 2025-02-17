package ca.ulaval.glo4002.application.infrastructure.initialization;

import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import ca.ulaval.glo4002.application.infrastructure.inMemory.FestivalRepositoryInMemory;
import ca.ulaval.glo4002.application.infrastructure.sqLite.FestivalRepositorySQLite;
import ca.ulaval.glo4002.application.infrastructure.sqLite.OrderMapperSQLite;
import ca.ulaval.glo4002.application.infrastructure.sqLite.config.DatabaseInitializer;
import ca.ulaval.glo4002.application.infrastructure.sqLite.config.SQLiteConnection;

import java.sql.Connection;

public class FestivalRepositoryFactory {


    public FestivalRepository createFestivalRepository(String persistenceType) {

        if (persistenceType.equals("sqlite")) {
            try {
                DatabaseInitializer.initialize(SQLiteConnection.getConnection());
                Connection connection = SQLiteConnection.getConnection();
                OrderMapperSQLite orderMapperSQLite = new OrderMapperSQLite();
                return new FestivalRepositorySQLite(connection, orderMapperSQLite);
            }
            catch (Exception e) {
                throw new RuntimeException("Failed to initialize SQLite persistence", e);
            }
        }
        else {
            return new FestivalRepositoryInMemory();

        }

    }

}
