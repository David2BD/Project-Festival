package ca.ulaval.glo4002.application.domain.oxygen;

import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategory;
import ca.ulaval.glo4002.application.domain.pass.options.PassOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FestivalClientTest {

    @Mock
    PassCategory passCategory;

    @Mock
    PassOption passOption;

    @Mock
    FestivalDates festivalDates;

    FestivalClient festivalClient;

    @BeforeEach
    void setUp() {
        festivalClient = new FestivalClient(passCategory, passOption);
    }

    @Test
    void givenAnyPass_whenGetBaseOxygenGrade_thenPassCategoryGetBaseOxygenGradeCalled() {
        festivalClient.getBaseOxygenGrade();

        verify(passCategory).getBaseOxygenGrade();
    }

    @Test
    void givenAnyPass_whenGetNumberOfTankNeededForPass_thenPassOptionCalculatePassDurationCalled() {
        festivalClient.calculateTanksNeeded(festivalDates);

        verify(passOption).calculatePassDuration(festivalDates);
    }

    @Test
    void givenAnyPass_whenGetNumberOfTankNeededForPass_thenPassCategoryGetOxygenDailyUseCalled() {
        festivalClient.calculateTanksNeeded(festivalDates);

        verify(passCategory).getOxygenDailyUse();
    }

    @Test
    void givenAnyPassDurationAndDailyOxygenUse_whenCalculateTanksNeeded_thenReturnsDailyOxygenUseTimesDuration() {
        int ANY_PASS_DURATION = 10;
        int ANY_OXYGEN_DAILY_USE = 5;
        when(passCategory.getOxygenDailyUse()).thenReturn(ANY_OXYGEN_DAILY_USE);
        when(passOption.calculatePassDuration(festivalDates)).thenReturn(ANY_PASS_DURATION);

        int expectedTanksNeeded = ANY_PASS_DURATION * ANY_OXYGEN_DAILY_USE;
        int tanksNeeded = festivalClient.calculateTanksNeeded(festivalDates);

        assertEquals(expectedTanksNeeded, tanksNeeded);
    }

}
