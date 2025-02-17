package ca.ulaval.glo4002.application.domain.pass.options;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import ca.ulaval.glo4002.application.domain.pass.exceptions.InvalidFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DailyPassOptionTest {
    private static final LocalDate VALID_EVENT_DATE = LocalDate.of(2060, 7, 19);
    private static final LocalDate FESTIVAL_START_DATE = LocalDate.of(2060, 7, 17);
    private static final LocalDate FESTIVAL_END_DATE = LocalDate.of(2060, 7, 24);
    private static final MoneyAmount DAILY_PASS_PRICE_STANDARD = new MoneyAmount(50000);
    private static final MoneyAmount DAILY_PASS_PRICE_PREMIUM = new MoneyAmount(100000);
    private static final MoneyAmount DAILY_PASS_PRICE_VIP = new MoneyAmount(150000);
    @Mock
    FestivalDates festivalDates;

    DailyPassOption dailyPassOption;

    @BeforeEach
    void setUp() {
        dailyPassOption = new DailyPassOption(VALID_EVENT_DATE);
    }

    @Test
    void givenVipCategory_whenGetPassPrice_thenReturnVipDailyPassPrice() {
        MoneyAmount passPrice = dailyPassOption.getPassPrice(PassCategoryTypes.VIP);

        assertEquals(DAILY_PASS_PRICE_VIP.getAmount(), passPrice.getAmount());
    }

    @Test
    void givenPremiumCategory_whenGetPassPrice_thenReturnPremiumDailyPassPrice() {
        MoneyAmount passPrice = dailyPassOption.getPassPrice(PassCategoryTypes.PREMIUM);

        assertEquals(DAILY_PASS_PRICE_PREMIUM.getAmount(), passPrice.getAmount());
    }

    @Test
    void givenStandardCategory_whenGetPassPrice_thenReturnStandardDailyPassPrice() {
        MoneyAmount passPrice = dailyPassOption.getPassPrice(PassCategoryTypes.STANDARD);

        assertEquals(DAILY_PASS_PRICE_STANDARD.getAmount(), passPrice.getAmount());
    }

    @Test
    void givenEventDateAtFestivalStartDate_whenValidateDailyPass_thenDoesNotThrowInvalidEventDateException() {
        LocalDate validDate = FESTIVAL_START_DATE;

        assertDoesNotThrow(() -> new DailyPassOption(validDate));
    }

    @Test
    void givenEventDateAtFestivalEndDate_whenValidateDailyPass_thenDoesNotThrowInvalidEventDateException() {
        LocalDate validDate = FESTIVAL_END_DATE;

        assertDoesNotThrow(() -> new DailyPassOption(validDate));
    }

    @Test
    void whenNullEventDate_thenThrowsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, () -> dailyPassOption = new DailyPassOption(null));
    }

    @Test
    void whenValidEventDate_thenDoesNotThrowInvalidFormatException() {
        assertDoesNotThrow(() -> dailyPassOption = new DailyPassOption(VALID_EVENT_DATE));
    }

    @Test
    void givenAnyDailyPassOption_whenCalculatePassDuration_thenReturnsOne() {
        int expectedDailyPassOptionDuration = 1;

        int passDuration = dailyPassOption.calculatePassDuration(festivalDates);

        assertEquals(expectedDailyPassOptionDuration, passDuration);
    }

    @Test
    void givenAnyDailyPassOption_whenGetEventDates_thenReturnsListContainingEventDate() {
        List<LocalDate> eventDates = dailyPassOption.getEventDates(FESTIVAL_START_DATE, FESTIVAL_END_DATE);

        assertTrue(eventDates.contains(VALID_EVENT_DATE), "The list should contain the event date.");
    }

    @Test
    void givenAnyDailyPassOption_whenGetEventDates_thenReturnsListOfOneEventDate() {
        List<LocalDate> eventDates = dailyPassOption.getEventDates(FESTIVAL_START_DATE, FESTIVAL_END_DATE);

        assertEquals(1, eventDates.size(), "The list should have one element in it.");
    }

    @Test
    void givenAnyDailyPassOption_whenCanBeUsedForDiscount_thenReturnsTrue() {
        boolean canBeUsedForDiscount = dailyPassOption.canBeUsedForDiscount();

        assertTrue(canBeUsedForDiscount);
    }
}