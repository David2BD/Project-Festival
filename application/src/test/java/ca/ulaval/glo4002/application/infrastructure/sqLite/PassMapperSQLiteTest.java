package ca.ulaval.glo4002.application.infrastructure.sqLite;

import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategory;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import ca.ulaval.glo4002.application.domain.pass.categories.PremiumPassCategory;
import ca.ulaval.glo4002.application.domain.pass.categories.StandardPassCategory;
import ca.ulaval.glo4002.application.domain.pass.categories.VipPassCategory;
import ca.ulaval.glo4002.application.domain.pass.options.DailyPassOption;
import ca.ulaval.glo4002.application.domain.pass.options.EventPassOption;
import ca.ulaval.glo4002.application.domain.pass.options.PassOption;
import ca.ulaval.glo4002.application.domain.pass.options.PassOptionTypes;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PassMapperSQLiteTest {
    private static final String DAILY_PASS_OPTION_STRING = "passe_journaliere";
    private static final String EVENT_PASS_OPTION_STRING = "forfait";
    private static final String VIP_PASS_CATEGORY_STRING = "VIP";
    private static final String PREMIUM_PASS_CATEGORY_STRING = "Premium";
    private static final String STANDARD_PASS_CATEGORY_STRING = "Standard";
    private static final String EVENT_DATE_STRING = "2021-07-01";
    private static final LocalDate EVENT_DATE = LocalDate.of(2021, 7, 1);
    private static final String PASS_NUMBER_STRING = "123";
    private static final String EVENT_DATE_NULL = null;
    private static final PassNumber PASS_NUMBER = new PassNumber(PASS_NUMBER_STRING);
    private PassSQLite passSQLite;
    private final PassOption DAILY_PASS_OPTION = new DailyPassOption(EVENT_DATE);
    private final PassOption EVENT_PASS_OPTION = new EventPassOption(null);
    private final PassCategory VIP_PASS_CATEGORY = new VipPassCategory();
    private final PassCategory PREMIUM_PASS_CATEGORY = new PremiumPassCategory();
    private final PassCategory STANDARD_PASS_CATEGORY = new StandardPassCategory();

    private final PassMapperSQLite passMapperSQLite = new PassMapperSQLite();

    @Test
    void givenADailyVIPPass_whenConvertToSQLite_thenPassSQLiteIsReturned() {
        Pass pass = new Pass(PASS_NUMBER, VIP_PASS_CATEGORY, DAILY_PASS_OPTION);

        PassSQLite passSQLite = passMapperSQLite.convertToSQLite(pass);

        assertEquals(DAILY_PASS_OPTION_STRING, passSQLite.passOption());
        assertEquals(VIP_PASS_CATEGORY_STRING, passSQLite.passCategory());
        assertEquals(EVENT_DATE_STRING, passSQLite.eventDate());
        assertEquals(PASS_NUMBER_STRING, passSQLite.passNumber());
    }

    @Test
    void givenADailyPremiumPass_whenConvertToSQLite_thenPassSQLiteIsReturned() {
        Pass pass = new Pass(PASS_NUMBER, PREMIUM_PASS_CATEGORY, DAILY_PASS_OPTION);

        PassSQLite passSQLite = passMapperSQLite.convertToSQLite(pass);

        assertEquals(DAILY_PASS_OPTION_STRING, passSQLite.passOption());
        assertEquals(PREMIUM_PASS_CATEGORY_STRING, passSQLite.passCategory());
        assertEquals(EVENT_DATE_STRING, passSQLite.eventDate());
        assertEquals(PASS_NUMBER_STRING, passSQLite.passNumber());
    }

    @Test
    void givenADailyStandardPass_whenConvertToSQLite_thenPassSQLiteIsReturned() {
        Pass pass = new Pass(PASS_NUMBER, STANDARD_PASS_CATEGORY, DAILY_PASS_OPTION);

        PassSQLite passSQLite = passMapperSQLite.convertToSQLite(pass);

        assertEquals(DAILY_PASS_OPTION_STRING, passSQLite.passOption());
        assertEquals(STANDARD_PASS_CATEGORY_STRING, passSQLite.passCategory());
        assertEquals(EVENT_DATE_STRING, passSQLite.eventDate());
        assertEquals(PASS_NUMBER_STRING, passSQLite.passNumber());
    }

    @Test
    void givenAnEventVIPPass_whenConvertToSQLite_thenPassSQLiteIsReturned() {
        Pass pass = new Pass(PASS_NUMBER, VIP_PASS_CATEGORY, EVENT_PASS_OPTION);

        PassSQLite passSQLite = passMapperSQLite.convertToSQLite(pass);

        assertEquals(EVENT_PASS_OPTION_STRING, passSQLite.passOption());
        assertEquals(VIP_PASS_CATEGORY_STRING, passSQLite.passCategory());
        assertEquals(EVENT_DATE_NULL, passSQLite.eventDate());
        assertEquals(PASS_NUMBER_STRING, passSQLite.passNumber());
    }

    @Test
    void givenAnEventPremiumPass_whenConvertToSQLite_thenPassSQLiteIsReturned() {
        Pass pass = new Pass(PASS_NUMBER, PREMIUM_PASS_CATEGORY, EVENT_PASS_OPTION);

        PassSQLite passSQLite = passMapperSQLite.convertToSQLite(pass);

        assertEquals(EVENT_PASS_OPTION_STRING, passSQLite.passOption());
        assertEquals(PREMIUM_PASS_CATEGORY_STRING, passSQLite.passCategory());
        assertEquals(EVENT_DATE_NULL, passSQLite.eventDate());
        assertEquals(PASS_NUMBER_STRING, passSQLite.passNumber());
    }

    @Test
    void givenAnEventStandardPass_whenConvertToSQLite_thenPassSQLiteIsReturned() {
        Pass pass = new Pass(PASS_NUMBER, STANDARD_PASS_CATEGORY, EVENT_PASS_OPTION);

        PassSQLite passSQLite = passMapperSQLite.convertToSQLite(pass);

        assertEquals(EVENT_PASS_OPTION_STRING, passSQLite.passOption());
        assertEquals(STANDARD_PASS_CATEGORY_STRING, passSQLite.passCategory());
        assertEquals(EVENT_DATE_NULL, passSQLite.eventDate());
        assertEquals(PASS_NUMBER_STRING, passSQLite.passNumber());
    }

    @Test
    void givenADailyVIPPass_whenConvertFromSQLite_thenPassIsReturned() {
        passSQLite = new PassSQLite(DAILY_PASS_OPTION_STRING, VIP_PASS_CATEGORY_STRING, EVENT_DATE_STRING,
                                    PASS_NUMBER_STRING);

        Pass pass = passMapperSQLite.convertFromSQLite(passSQLite);

        assertEquals(PassOptionTypes.DAILY, pass.getPassOption());
        assertEquals(PassCategoryTypes.VIP, pass.getPassCategory());
        assertEquals(EVENT_DATE, pass.getEventDate());
        assertEquals(PASS_NUMBER_STRING, pass.getPassNumberValue());
    }

    @Test
    void givenADailyPremiumPass_whenConvertFromSQLite_thenPassIsReturned() {
        passSQLite = new PassSQLite(DAILY_PASS_OPTION_STRING, PREMIUM_PASS_CATEGORY_STRING, EVENT_DATE_STRING,
                                    PASS_NUMBER_STRING);

        Pass pass = passMapperSQLite.convertFromSQLite(passSQLite);

        assertEquals(PassOptionTypes.DAILY, pass.getPassOption());
        assertEquals(PassCategoryTypes.PREMIUM, pass.getPassCategory());
        assertEquals(EVENT_DATE, pass.getEventDate());
        assertEquals(PASS_NUMBER_STRING, pass.getPassNumberValue());
    }

    @Test
    void givenADailyStandardPass_whenConvertFromSQLite_thenPassIsReturned() {
        passSQLite = new PassSQLite(DAILY_PASS_OPTION_STRING, STANDARD_PASS_CATEGORY_STRING, EVENT_DATE_STRING,
                                    PASS_NUMBER_STRING);

        Pass pass = passMapperSQLite.convertFromSQLite(passSQLite);

        assertEquals(PassOptionTypes.DAILY, pass.getPassOption());
        assertEquals(PassCategoryTypes.STANDARD, pass.getPassCategory());
        assertEquals(EVENT_DATE, pass.getEventDate());
        assertEquals(PASS_NUMBER_STRING, pass.getPassNumberValue());
    }

    @Test
    void givenAnEventVIPPass_whenConvertFromSQLite_thenPassIsReturned() {
        passSQLite =
                new PassSQLite(EVENT_PASS_OPTION_STRING, VIP_PASS_CATEGORY_STRING, EVENT_DATE_NULL, PASS_NUMBER_STRING);

        Pass pass = passMapperSQLite.convertFromSQLite(passSQLite);

        assertEquals(PassOptionTypes.EVENT, pass.getPassOption());
        assertEquals(PassCategoryTypes.VIP, pass.getPassCategory());
        assertNull(pass.getEventDate());
        assertEquals(PASS_NUMBER_STRING, pass.getPassNumberValue());
    }

    @Test
    void givenAnEventPremiumPass_whenConvertFromSQLite_thenPassIsReturned() {
        passSQLite = new PassSQLite(EVENT_PASS_OPTION_STRING, PREMIUM_PASS_CATEGORY_STRING, EVENT_DATE_NULL,
                                    PASS_NUMBER_STRING);

        Pass pass = passMapperSQLite.convertFromSQLite(passSQLite);

        assertEquals(PassOptionTypes.EVENT, pass.getPassOption());
        assertEquals(PassCategoryTypes.PREMIUM, pass.getPassCategory());
        assertNull(pass.getEventDate());
        assertEquals(PASS_NUMBER_STRING, pass.getPassNumberValue());
    }

    @Test
    void givenAnEventStandardPass_whenConvertFromSQLite_thenPassIsReturned() {
        passSQLite = new PassSQLite(EVENT_PASS_OPTION_STRING, STANDARD_PASS_CATEGORY_STRING, EVENT_DATE_NULL,
                                    PASS_NUMBER_STRING);

        Pass pass = passMapperSQLite.convertFromSQLite(passSQLite);

        assertEquals(PassOptionTypes.EVENT, pass.getPassOption());
        assertEquals(PassCategoryTypes.STANDARD, pass.getPassCategory());
        assertNull(pass.getEventDate());
        assertEquals(PASS_NUMBER_STRING, pass.getPassNumberValue());
    }
}