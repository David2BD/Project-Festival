package ca.ulaval.glo4002.application.domain.pass.categories;

import ca.ulaval.glo4002.application.domain.pass.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PassCategoryTypesTest {
    private static final String VIP_CATEGORY_STRING_NAME = "VIP";
    private static final String PREMIUM_CATEGORY_STRING_NAME = "Premium";
    private static final String STANDARD_CATEGORY_STRING_NAME = "Standard";

    @Test
    void givenVipCategory_whenToString_thenReturnVipStringName() {
        String stringName = PassCategoryTypes.VIP.toString();

        assertEquals(VIP_CATEGORY_STRING_NAME, stringName);
    }

    @Test
    void givenPremiumCategory_whenToString_thenReturnPremiumStringName() {
        String stringName = PassCategoryTypes.PREMIUM.toString();

        assertEquals(PREMIUM_CATEGORY_STRING_NAME, stringName);
    }

    @Test
    void givenStandardCategory_whenToString_thenReturnStandardStringName() {
        String stringName = PassCategoryTypes.STANDARD.toString();

        assertEquals(STANDARD_CATEGORY_STRING_NAME, stringName);
    }

    @Test
    void givenValidVipCategoryName_whenFromString_thenReturnVipType() {
        PassCategoryTypes category = PassCategoryTypes.fromString(VIP_CATEGORY_STRING_NAME);

        assertEquals(PassCategoryTypes.VIP, category);
    }

    @Test
    void givenValidPremiumCategoryName_whenFromString_thenReturnPremiumType() {
        PassCategoryTypes category = PassCategoryTypes.fromString(PREMIUM_CATEGORY_STRING_NAME);

        assertEquals(PassCategoryTypes.PREMIUM, category);
    }

    @Test
    void givenValidStandardCategoryName_whenFromString_thenReturnStandardType() {
        PassCategoryTypes category = PassCategoryTypes.fromString(STANDARD_CATEGORY_STRING_NAME);

        assertEquals(PassCategoryTypes.STANDARD, category);
    }

    @Test
    void givenInvalidCategoryName_whenFromString_thenThrowInvalidFormatException() {
        String invalidName = "invalid";

        assertThrows(InvalidFormatException.class, () -> PassCategoryTypes.fromString(invalidName));
    }

    @Test
    void givenCategoryNameWithInvalidCase_whenFromString_thenThrowInvalidFormatException() {
        String invalidCase = PREMIUM_CATEGORY_STRING_NAME.toLowerCase();

        assertThrows(InvalidFormatException.class, () -> PassCategoryTypes.fromString(invalidCase));
    }
}