package ca.ulaval.glo4002.application.domain.festival;

import ca.ulaval.glo4002.application.domain.pass.exceptions.InvalidEventDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FestivalDatesTest {

    private static final LocalDate DEFAULT_FESTIVAL_OPENING_DATE = LocalDate.of(2060, 7, 17);
    private static final LocalDate DEFAULT_FESTIVAL_CLOSING_DATE = LocalDate.of(2060, 7, 24);
    private static final String DEFAULT_FESTIVAL_OPENING_DATE_STRING = "July 17 2060";
    private static final String DEFAULT_FESTIVAL_CLOSING_DATE_STRING = "July 24 2060";
    private FestivalDates festivalDates;

    @BeforeEach
    void setUp() {
        festivalDates = new FestivalDates(DEFAULT_FESTIVAL_OPENING_DATE, DEFAULT_FESTIVAL_CLOSING_DATE);
    }

    @Test
    void whenGetStartDate_thenReturnCorrectStartDate() {
        LocalDate startDate = festivalDates.getStartDate();

        assertEquals(DEFAULT_FESTIVAL_OPENING_DATE, startDate);
    }

    @Test
    void whenGetEndDate_thenReturnCorrectEndDate() {
        LocalDate endDate = festivalDates.getEndDate();

        assertEquals(DEFAULT_FESTIVAL_CLOSING_DATE, endDate);
    }

    @Test
    void whenGetFestivalDurationInDays_thenReturnEight() {
        int festivalDuration = festivalDates.getFestivalDurationInDays();

        assertEquals(8, festivalDuration);
    }

    @Test
    void givenValidDate_whenVerifyEventDate_thenDoesNotThrowException() {
        LocalDate validDate = LocalDate.of(2060, 7, 18);

        assertDoesNotThrow(() -> festivalDates.verifyEventDate(validDate));
    }

    @Test
    void givenInvalidDateBeforeFestival_whenVerifyEventDate_thenThrowInvalidEventDateException() {
        LocalDate invalidDateBeforeFestival = LocalDate.of(2059, 7, 16);

        InvalidEventDateException exception = assertThrows(InvalidEventDateException.class, () -> {
            festivalDates.verifyEventDate(invalidDateBeforeFestival);
        });

        assertEquals("event date should be between " +
                     DEFAULT_FESTIVAL_OPENING_DATE_STRING +
                     " and " +
                     DEFAULT_FESTIVAL_CLOSING_DATE_STRING, exception.getMessage());
    }

    @Test
    void givenInvalidDateAfterFestival_whenVerifyEventDate_thenThrowInvalidEventDateException() {
        LocalDate invalidDateAfterFestival = LocalDate.of(2061, 7, 25);

        InvalidEventDateException exception = assertThrows(InvalidEventDateException.class, () -> {
            festivalDates.verifyEventDate(invalidDateAfterFestival);
        });

        assertEquals("event date should be between " +
                     DEFAULT_FESTIVAL_OPENING_DATE_STRING +
                     " and " +
                     DEFAULT_FESTIVAL_CLOSING_DATE_STRING, exception.getMessage());
    }
}



