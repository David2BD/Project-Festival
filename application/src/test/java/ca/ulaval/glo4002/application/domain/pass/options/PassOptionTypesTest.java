package ca.ulaval.glo4002.application.domain.pass.options;

import ca.ulaval.glo4002.application.domain.pass.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PassOptionTypesTest {
    private static final String DAILY_OPTION_STRING_NAME = "passe_journaliere";
    private static final String EVENT_OPTION_STRING_NAME = "forfait";

    @Test
    void givenDailyOption_whenToString_thenReturnDailyStringName() {
        String stringName = PassOptionTypes.DAILY.toString();

        assertEquals(DAILY_OPTION_STRING_NAME, stringName);
    }

    @Test
    void givenEventOption_whenToString_thenReturnEventStringName() {
        String stringName = PassOptionTypes.EVENT.toString();

        assertEquals(EVENT_OPTION_STRING_NAME, stringName);
    }

    @Test
    void givenInvalidOptionName_whenFromString_thenThrowInvalidFormatException() {
        String invalidName = "invalid";

        assertThrows(InvalidFormatException.class, () -> PassOptionTypes.fromString(invalidName));
    }

    @Test
    void givenOptionNameWithInvalidCase_whenFromString_thenThrowInvalidFormatException() {
        String invalidCase = DAILY_OPTION_STRING_NAME.toUpperCase();

        assertThrows(InvalidFormatException.class, () -> PassOptionTypes.fromString(invalidCase));
    }

    @Test
    void givenValidDailyOptionName_whenFromString_thenReturnDailyOptionType() {
        PassOptionTypes passOption = PassOptionTypes.fromString(DAILY_OPTION_STRING_NAME);

        assertEquals(PassOptionTypes.DAILY, passOption);
    }

    @Test
    void givenValidEventOptionName_whenFromString_thenReturnDailyOptionType() {
        PassOptionTypes passOption = PassOptionTypes.fromString(EVENT_OPTION_STRING_NAME);

        assertEquals(PassOptionTypes.EVENT, passOption);
    }

}