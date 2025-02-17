package ca.ulaval.glo4002.application.infrastructure.sqLite;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.order.OrderDates;
import ca.ulaval.glo4002.application.infrastructure.sqLite.config.DatabaseInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FestivalRepositorySQLiteTest {
    private static final String IN_MEMORY_DB_URL = "jdbc:sqlite::memory:";
    private Connection connection;
    private FestivalRepositorySQLite repository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(IN_MEMORY_DB_URL);
        DatabaseInitializer.initialize(connection);
        repository = new FestivalRepositorySQLite(connection, new OrderMapperSQLite());
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void addFestival_shouldAddFestivalToDatabaseWhenItDoesNotExist() {
        FestivalDates festivalDates = new FestivalDates(LocalDate.of(2060, 7, 17), LocalDate.of(2060, 7, 24));
        OrderDates orderDates = new OrderDates(ZonedDateTime.parse("2060-01-01T00:00:00Z"),
                                               ZonedDateTime.parse("2060-07-16T23:59:59Z"));
        Festival festival = new Festival("1", "Test Festival", festivalDates, orderDates, "TEST_VENDOR");

        repository.addFestival(festival);

        Festival retrievedFestival = repository.getFestival();
        assertNotNull(retrievedFestival, "Festival should be added to the database.");
        assertEquals(festival.getId(), retrievedFestival.getId(), "Festival ID should match.");
        assertEquals(festival.getName(), retrievedFestival.getName(), "Festival name should match.");
        assertEquals(festival.getOrdersOpeningDate().withZoneSameInstant(ZoneId.of("GMT")),
                     retrievedFestival.getOrdersOpeningDate().withZoneSameInstant(ZoneId.of("GMT")),
                     "Orders opening date should match.");
        assertEquals(festival.getOrdersClosingDate().withZoneSameInstant(ZoneId.of("GMT")),
                     retrievedFestival.getOrdersClosingDate().withZoneSameInstant(ZoneId.of("GMT")),
                     "Orders closing date should match.");
    }

}
