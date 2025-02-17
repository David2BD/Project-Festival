package ca.ulaval.glo4002.application.infrastructure.initialization;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import ca.ulaval.glo4002.application.domain.order.OrderDates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class FestivalInitializer {
    private static final Logger logger = LoggerFactory.getLogger(FestivalInitializer.class);
    private final FestivalRepository festivalRepository;
    private final Connection sqliteConnection;

    public FestivalInitializer(FestivalRepository festivalRepository, Connection sqliteConnection) {
        this.festivalRepository = festivalRepository;
        this.sqliteConnection = sqliteConnection;
    }

    public void initialize() {
        if (sqliteConnection != null) {
            initializeForSQLite();
        }
        else {
            initializeForInMemory();
        }
    }

    private void initializeForSQLite() {
        if (festivalRepository.getFestival() == null) {
            Festival defaultFestival = createDefaultFestival();
            festivalRepository.addFestival(defaultFestival);
            logger.info("Default festival added to SQLite: " + defaultFestival);
        }
        else {
            logger.info("Default festival already exists in SQLite.");
        }
    }

    private void initializeForInMemory() {
        if (festivalRepository.getFestival() == null) {
            Festival defaultFestival = createDefaultFestival();
            festivalRepository.addFestival(defaultFestival);
        }
        else {
            logger.info("Default festival already exists in in-memory storage.");
        }
    }

    public static Festival createDefaultFestival() {
        final String DEFAULT_FESTIVAL_ID = "GLOW-4002-DEFAULT";
        final String DEFAULT_FESTIVAL_NAME = "GLOW-4002";
        final ZonedDateTime DEFAULT_ORDERS_OPENING_DATE = ZonedDateTime.of(2060, 1, 1, 0, 0, 0, 0, ZoneId.of("GMT"));
        final ZonedDateTime DEFAULT_ORDERS_CLOSING_DATE =
                ZonedDateTime.of(2060, 7, 16, 23, 59, 59, 999_999_999, ZoneId.of("GMT"));
        final LocalDate DEFAULT_FESTIVAL_START_DATE = LocalDate.of(2060, 7, 17);
        final LocalDate DEFAULT_FESTIVAL_END_DATE = LocalDate.of(2060, 7, 24);
        final String DEFAULT_VENDOR_CODE = "TEAM";

        FestivalDates festivalDates = new FestivalDates(DEFAULT_FESTIVAL_START_DATE, DEFAULT_FESTIVAL_END_DATE);
        OrderDates orderDates = new OrderDates(DEFAULT_ORDERS_OPENING_DATE, DEFAULT_ORDERS_CLOSING_DATE);
        return new Festival(DEFAULT_FESTIVAL_ID, DEFAULT_FESTIVAL_NAME, festivalDates, orderDates, DEFAULT_VENDOR_CODE);
    }
}
