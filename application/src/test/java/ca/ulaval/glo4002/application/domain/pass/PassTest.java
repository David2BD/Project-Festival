package ca.ulaval.glo4002.application.domain.pass;

import ca.ulaval.glo4002.application.domain.pass.categories.PassCategory;
import ca.ulaval.glo4002.application.domain.pass.options.PassOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PassTest {
    private static final LocalDate FESTIVAL_START_DATE = LocalDate.of(2060, 7, 17);
    private static final LocalDate FESTIVAL_END_DATE = LocalDate.of(2060, 7, 24);

    @Mock
    PassCategory passCategory;

    @Mock
    PassOption passOption;

    Pass pass;

    @BeforeEach
    void setUp() {
        pass = new Pass(passCategory, passOption);
    }

    @Test
    void givenAnyPass_whenGetPassCategory_thenPassCategoryGetPassCategoryCalled() {
        pass.getPassCategory();

        verify(passCategory).getPassCategory();
    }

    @Test
    void givenAnyPass_whenGetPassPrice_thenPassOptionGetPassPriceCalled() {
        pass.getPassPrice();

        verify(passOption).getPassPrice(passCategory.getPassCategory());
    }

    @Test
    void givenAnyPass_whenCanBeUsedForDiscount_thenPassOptionCanBeUsedForDiscountCalled() {
        pass.canBeUsedForDiscount();

        verify(passOption).canBeUsedForDiscount();
    }

    @Test
    void givenAnyPass_whenGetEventDates_thenPassOptionGetEventDatesCalled() {
        pass.getEventDates(FESTIVAL_START_DATE, FESTIVAL_END_DATE);

        verify(passOption).getEventDates(FESTIVAL_START_DATE, FESTIVAL_END_DATE);
    }

    @Test
    void givenAnyPass_whenGetEventDate_thenPassOptionGetEventDateCalled() {
        pass.getEventDate();

        verify(passOption).getEventDate();
    }

    @Test
    void givenAnyPass_whenGetPassOption_thenPassOptionGetOptionTypeCalled() {
        pass.getPassOption();

        verify(passOption).getOptionType();
    }

}
