package ca.ulaval.glo4002.application.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderDatesTest {
    private final ZonedDateTime OPENING_BUYABLE_RANGE_DATE = ZonedDateTime.of(2060, 1, 1, 0, 0, 0, 0, ZoneId.of("GMT"));
    private final ZonedDateTime CLOSING_BUYABLE_RANGE_DATE =
            ZonedDateTime.of(2060, 7, 16, 23, 59, 59, 999_999_999, ZoneId.of("GMT"));
    private final ZonedDateTime DATE_AT_THE_TIME_OF_BUYABLE_RANGE =
            ZonedDateTime.of(2060, 5, 25, 0, 0, 0, 0, ZoneId.of("GMT"));
    private final ZonedDateTime DATE_BEFORE_OPENING_BUYABLE_RANGE =
            ZonedDateTime.of(2059, 12, 31, 0, 0, 0, 0, ZoneId.of("GMT"));
    private final ZonedDateTime DATE_AFTER_CLOSING_BUYABLE_RANGE =
            ZonedDateTime.of(2060, 7, 17, 23, 59, 59, 999_999_999, ZoneId.of("GMT"));
    private OrderDates orderDates;
    private static final String DEFAULT_OPENING_BUYABLE_RANGE_DATE_STRING = "January 1 2060";
    private static final String DEFAULT_CLOSING_BUYABLE_RANGE_DATE_STRING = "July 16 2060";

    @BeforeEach
    void setUp() {
        orderDates = new OrderDates(OPENING_BUYABLE_RANGE_DATE, CLOSING_BUYABLE_RANGE_DATE);
    }

    @Test
    void givenValidDateAtTheTimeOfOpeningBuyableRange_whenValidatingDate_thenDoesNotThrowException() {
        ZonedDateTime validDateAtTheTimeOfOpeningBuyableRange = OPENING_BUYABLE_RANGE_DATE;

        assertDoesNotThrow(() -> orderDates.validateOrderDateIsInBuyableRange(validDateAtTheTimeOfOpeningBuyableRange));
    }

    @Test
    void givenValidDateAtTheTimeOfClosingBuyableRange_whenValidatingDate_thenDoesNotThrowException() {
        ZonedDateTime validDateAtTheTimeOfClosingBuyableRange = CLOSING_BUYABLE_RANGE_DATE;

        assertDoesNotThrow(() -> orderDates.validateOrderDateIsInBuyableRange(validDateAtTheTimeOfClosingBuyableRange));
    }

    @Test
    void givenValidDateAtTheTimeOfBuyableRange_whenValidatingOrderDate_thenDoesNotThrowException() {
        ZonedDateTime validDateAtTheTimeOfBuyableRange = DATE_AT_THE_TIME_OF_BUYABLE_RANGE;

        assertDoesNotThrow(() -> orderDates.validateOrderDateIsInBuyableRange(validDateAtTheTimeOfBuyableRange));
    }

    @Test
    void givenInvalidDateBeforeOpeningBuyableRange_whenValidatingOrderDate_thenThrowInvalidOrderDateException() {
        ZonedDateTime invalidDateBeforeOpeningBuyableRange = DATE_BEFORE_OPENING_BUYABLE_RANGE;

        InvalidOrderDateException exception = assertThrows(InvalidOrderDateException.class, () -> {
            orderDates.validateOrderDateIsInBuyableRange(invalidDateBeforeOpeningBuyableRange);
        });

        assertEquals("order date should be between " +
                     DEFAULT_OPENING_BUYABLE_RANGE_DATE_STRING +
                     " and " +
                     DEFAULT_CLOSING_BUYABLE_RANGE_DATE_STRING, exception.getMessage());
    }

    @Test
    void givenInvalidDateAfterClosingBuyableRange_whenValidatingOrderDate_thenThrowInvalidOrderDateException() {
        ZonedDateTime invalidDateAfterClosingBuyableRange = DATE_AFTER_CLOSING_BUYABLE_RANGE;

        InvalidOrderDateException exception = assertThrows(InvalidOrderDateException.class, () -> {
            orderDates.validateOrderDateIsInBuyableRange(invalidDateAfterClosingBuyableRange);
        });

        assertEquals("order date should be between " +
                     DEFAULT_OPENING_BUYABLE_RANGE_DATE_STRING +
                     " and " +
                     DEFAULT_CLOSING_BUYABLE_RANGE_DATE_STRING, exception.getMessage());
    }

}
