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
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventPassOptionTest {
    private static final LocalDate VALID_EVENT_DATE = null;
    private static final LocalDate INVALID_EVENT_DATE = LocalDate.of(2060, 7, 17);
    private static final LocalDate FESTIVAL_START_DATE = LocalDate.of(2060, 7, 17);
    private static final LocalDate FESTIVAL_END_DATE = LocalDate.of(2060, 7, 24);
    private static final MoneyAmount EVENT_PASS_PRICE_STANDARD = new MoneyAmount(250000);
    private static final MoneyAmount EVENT_PASS_PRICE_PREMIUM = new MoneyAmount(500000);
    private static final MoneyAmount EVENT_PASS_PRICE_VIP = new MoneyAmount(700000);
    @Mock
    FestivalDates festivalDates;

    EventPassOption eventPassOption;

    @BeforeEach
    void setUp() {
        eventPassOption = new EventPassOption(VALID_EVENT_DATE);
    }

    @Test
    void givenVipCategory_whenGetPassPrice_thenReturnVipDailyPassPrice() {
        MoneyAmount passPrice = eventPassOption.getPassPrice(PassCategoryTypes.VIP);

        assertEquals(EVENT_PASS_PRICE_VIP.getAmount(), passPrice.getAmount());
    }

    @Test
    void givenPremiumCategory_whenGetPassPrice_thenReturnPremiumDailyPassPrice() {
        MoneyAmount passPrice = eventPassOption.getPassPrice(PassCategoryTypes.PREMIUM);

        assertEquals(EVENT_PASS_PRICE_PREMIUM.getAmount(), passPrice.getAmount());
    }

    @Test
    void givenStandardCategory_whenGetPassPrice_thenReturnStandardDailyPassPrice() {
        MoneyAmount passPrice = eventPassOption.getPassPrice(PassCategoryTypes.STANDARD);

        assertEquals(EVENT_PASS_PRICE_STANDARD.getAmount(), passPrice.getAmount());
    }

    @Test
    void whenNullEventDate_thenDoesNotThrowInvalidFormatException() {
        assertDoesNotThrow(() -> eventPassOption = new EventPassOption(VALID_EVENT_DATE));
    }

    @Test
    void whenEventDateNotNull_thenThrowsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, () -> eventPassOption = new EventPassOption(INVALID_EVENT_DATE));
    }

    @Test
    void givenAnyEventPassOption_whenCalculatePassDuration_thenReturnsDurationOfFestival() {
        int expectedEventPassOptionDuration = calculateFestivalDuration();
        when(festivalDates.getFestivalDurationInDays()).thenReturn(calculateFestivalDuration());

        int passDuration = eventPassOption.calculatePassDuration(festivalDates);

        assertEquals(expectedEventPassOptionDuration, passDuration);
    }

    @Test
    void givenAnyEventPassOption_whenCanBeUsedForDiscount_thenReturnsFalse() {
        boolean canBeUsedForDiscount = eventPassOption.canBeUsedForDiscount();

        assertFalse(canBeUsedForDiscount);
    }

    private int calculateFestivalDuration() {
        return ((int) ChronoUnit.DAYS.between(FESTIVAL_START_DATE, FESTIVAL_END_DATE) + 1);
    }
}