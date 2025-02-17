package ca.ulaval.glo4002.application.infrastructure.sqLite.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static final Logger logger = LoggerFactory.getLogger(SQLiteConnection.class);

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:sqlite:GLOW-4002.db");
            logger.info("Connection to SQLite has been established.");
        }
        return connection;
    }
}
