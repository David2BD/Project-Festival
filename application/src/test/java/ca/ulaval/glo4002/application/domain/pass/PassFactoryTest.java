package ca.ulaval.glo4002.application.domain.pass;

import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import ca.ulaval.glo4002.application.domain.pass.options.PassOptionTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PassFactoryTest {

    private static final String ANY_VALID_DATE = "2060-07-17";
    private static final LocalDate VALID_DATE_LOCALDATE = LocalDate.parse(ANY_VALID_DATE);
    private static final String VALID_DATE_FOR_EVENT_PASS = null;
    private static final String VIP_PASS_CATEGORY = "VIP";
    private static final String STANDARD_PASS_CATEGORY = "Standard";
    private static final String PREMIUM_PASS_CATEGORY = "Premium";
    private static final String EVENT_PASS_OPTION = "forfait";
    private static final String DAILY_PASS_OPTION = "passe_journaliere";
    private PassFactory passFactory;

    @BeforeEach
    void setup() {
        passFactory = new PassFactory();
    }

    @Test
    void givenEventPassOption_whenCreatePass_thenReturnEventPass() {
        Pass pass = passFactory.createPass(VIP_PASS_CATEGORY, EVENT_PASS_OPTION, VALID_DATE_FOR_EVENT_PASS);

        assertEquals(PassOptionTypes.EVENT, pass.getPassOption());
    }

    @Test
    void givenDailyPassOption_whenCreatePass_returnDailyPass() {
        Pass pass = passFactory.createPass(VIP_PASS_CATEGORY, DAILY_PASS_OPTION, ANY_VALID_DATE);

        assertEquals(PassOptionTypes.DAILY, pass.getPassOption());
    }

    @Test
    void givenValidDate_whenCreatingDailyPass_thenPassHasCorrectDate() {
        Pass pass = passFactory.createPass(VIP_PASS_CATEGORY, DAILY_PASS_OPTION, ANY_VALID_DATE);

        assertEquals(VALID_DATE_LOCALDATE, pass.getEventDate());
    }

    @Test
    void givenAnyOptionAndVipCategory_whenCreatePass_thenPassHasVipCategory() {
        Pass pass = passFactory.createPass(VIP_PASS_CATEGORY, DAILY_PASS_OPTION, ANY_VALID_DATE);

        assertEquals(PassCategoryTypes.VIP, pass.getPassCategory());
    }

    @Test
    void givenAnyOptionAndPremiumCategory_whenCreatePass_thenPassHasPremiumCategory() {
        Pass pass = passFactory.createPass(PREMIUM_PASS_CATEGORY, DAILY_PASS_OPTION, ANY_VALID_DATE);

        assertEquals(PassCategoryTypes.PREMIUM, pass.getPassCategory());
    }

    @Test
    void givenAnyOptionAndStandardCategory_whenCreatePass_thenPassHasStandardCategory() {
        Pass pass = passFactory.createPass(STANDARD_PASS_CATEGORY, DAILY_PASS_OPTION, ANY_VALID_DATE);

        assertEquals(PassCategoryTypes.STANDARD, pass.getPassCategory());
    }
}
