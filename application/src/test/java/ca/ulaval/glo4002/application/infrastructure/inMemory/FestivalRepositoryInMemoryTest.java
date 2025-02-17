package ca.ulaval.glo4002.application.infrastructure.inMemory;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.order.OrderDates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FestivalRepositoryInMemoryTest {

    private FestivalRepositoryInMemory festivalRepositoryInMemory;

    @BeforeEach
    void setUp() {
        festivalRepositoryInMemory = new FestivalRepositoryInMemory();
    }

    @Test
    void addFestival_shouldAddFestivalToRepository() {
        FestivalDates festivalDates = new FestivalDates(LocalDate.of(2060, 7, 17), LocalDate.of(2060, 7, 24));
        OrderDates orderDates = new OrderDates(ZonedDateTime.of(2060, 1, 1, 0, 0, 0, 0, ZoneId.of("GMT")),
                                               ZonedDateTime.of(2060, 7, 16, 23, 59, 59, 999_999_999,
                                                                ZoneId.of("GMT")));
        Festival festival = new Festival("1", "Test Festival", festivalDates, orderDates, "TEAM");
        festivalRepositoryInMemory.addFestival(festival);

        Festival retrievedFestival = festivalRepositoryInMemory.getFestival();
        assertNotNull(retrievedFestival, "Festival should be added to the repository.");
        assertEquals(festival, retrievedFestival, "Added festival should match the retrieved festival.");
    }
}
