package ca.ulaval.glo4002.application.infrastructure.sqLite.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSchema {
    public static final String CREATE_ORDERS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS orders (\n" +
                                                         "    order_number LONG PRIMARY KEY,\n" +
                                                         "    order_date TEXT NOT NULL,\n" +
                                                         "    vendor_code TEXT NOT NULL,\n" +
                                                         "    fk_festival_id TEXT NOT NULL,\n" +
                                                         "    FOREIGN KEY(fk_festival_id) REFERENCES festival(festival_id) ON DELETE CASCADE\n" +
                                                         ");";

    public static final String CREATE_PASSES_TABLE_SQL = "CREATE TABLE IF NOT EXISTS passes (\n" +
                                                         "    pass_number text PRIMARY KEY,\n" +
                                                         "    fk_order_number long NOT NULL,\n" +
                                                         "    pass_option text NOT NULL,\n" +
                                                         "    pass_category text NOT NULL,\n" +
                                                         "    event_date TEXT,  \n" +
                                                         "    FOREIGN KEY(fk_order_number) REFERENCES orders(order_number) ON DELETE CASCADE\n" +
                                                         ");";

    public static final String CREATE_FESTIVAL_TABLE = "CREATE TABLE IF NOT EXISTS festival (\n" +
                                                       "    festival_id TEXT PRIMARY KEY,\n" +
                                                       "    festival_name TEXT NOT NULL,\n" +
                                                       "    festival_start_date DATE NOT NULL,\n" +
                                                       "    festival_end_date DATE NOT NULL,\n" +
                                                       "    orders_opening_date TIMESTAMP NOT NULL,\n" +
                                                       "    orders_closing_date TIMESTAMP NOT NULL,\n" +
                                                       "    festival_vendor_code TEXT NOT NULL\n" +
                                                       ");";

    public static final String CREATE_CONFIRMED_PROGRAM_TABLE =
            "CREATE TABLE IF NOT EXISTS confirmed_program (" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "    festival_id TEXT NOT NULL, " +
            "    program_date DATE NOT NULL, " +
            "    artist_name TEXT NOT NULL, " +
            "    artist_cost DECIMAL(50,2) NOT NULL, " +
            "    popularity INTEGER NOT NULL, " +
            "    group_people_count INTEGER NOT NULL, " +
            "    musical_genre TEXT NOT NULL, " +
            "    shuttle_type TEXT NOT NULL, " +
            "    FOREIGN KEY(festival_id) REFERENCES festival(festival_id) ON DELETE CASCADE" +
            ");";


    public static final String CREATE_PROGRAM_DETAILS_TABLE = "CREATE TABLE IF NOT EXISTS program_details (" +
                                                              "    festival_id TEXT PRIMARY KEY, " +
                                                              "    artists_total_cost DECIMAL(50,2) NOT NULL DEFAULT 0.00, " +
                                                              "    program_is_confirmed BOOLEAN NOT NULL DEFAULT 0, " +
                                                              "    confirmation_date DATE, " +
                                                              "    FOREIGN KEY(festival_id) REFERENCES festival(festival_id) ON DELETE CASCADE" +
                                                              ");";

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    public static void initializeSchema(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            logger.info("Initializing database schema...");
            stmt.execute(CREATE_FESTIVAL_TABLE);
            stmt.execute(CREATE_ORDERS_TABLE_SQL);
            stmt.execute(CREATE_PASSES_TABLE_SQL);
            stmt.execute(CREATE_CONFIRMED_PROGRAM_TABLE);
            stmt.execute(CREATE_PROGRAM_DETAILS_TABLE);
        }
        catch (SQLException e) {
            throw new RuntimeException("Error initializing database schema", e);
        }
    }
}
