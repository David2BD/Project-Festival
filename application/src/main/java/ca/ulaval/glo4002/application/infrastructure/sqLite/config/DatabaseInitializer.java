package ca.ulaval.glo4002.application.infrastructure.sqLite.config;

import java.sql.Connection;

public class DatabaseInitializer {
    public static void initialize(Connection connection) {
        DatabaseSchema.initializeSchema(connection);
    }
}
