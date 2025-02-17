package ca.ulaval.glo4002.application.utils;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyUtilsTest {

    private static final String VALID_BUDGET_STRING = "123.45";
    private static final String VALID_BUDGET_WITH_SPACES = "  123.45  ";
    private static final String VALID_BUDGET_WITH_LINE_BREAKS = "\n123.45\n";
    private static final String INVALID_BUDGET_STRING = "abc";
    private static final String EMPTY_BUDGET_STRING = "";
    private static final String NULL_BUDGET_STRING = null;
    private static final BigDecimal EXPECTED_VALID_AMOUNT = new BigDecimal("123.45");
    private static final BigDecimal EXPECTED_ZERO_AMOUNT = BigDecimal.ZERO;
    private static final String EXPECTED_INVALID_FORMAT_MESSAGE = "Invalid format for headliner budget";

    @Test
    void givenValidBudgetString_whenParseMoneyAmount_thenReturnsCorrectMoneyAmount() {
        MoneyAmount moneyAmount = MoneyUtils.parseMoneyAmount(VALID_BUDGET_STRING);

        assertEquals(EXPECTED_VALID_AMOUNT, moneyAmount.getAmount());
    }

    @Test
    void givenValidBudgetStringWithSpaces_whenParseMoneyAmount_thenTrimsAndReturnsCorrectMoneyAmount() {
        MoneyAmount moneyAmount = MoneyUtils.parseMoneyAmount(VALID_BUDGET_WITH_SPACES);

        assertEquals(EXPECTED_VALID_AMOUNT, moneyAmount.getAmount());
    }

    @Test
    void givenNullBudgetString_whenParseMoneyAmount_thenReturnsZeroMoneyAmount() {
        MoneyAmount moneyAmount = MoneyUtils.parseMoneyAmount(NULL_BUDGET_STRING);

        assertEquals(EXPECTED_ZERO_AMOUNT, moneyAmount.getAmount());
    }

    @Test
    void givenEmptyBudgetString_whenParseMoneyAmount_thenReturnsZeroMoneyAmount() {
        MoneyAmount moneyAmount = MoneyUtils.parseMoneyAmount(EMPTY_BUDGET_STRING);

        assertEquals(EXPECTED_ZERO_AMOUNT, moneyAmount.getAmount());
    }

    @Test
    void givenInvalidBudgetString_whenParseMoneyAmount_thenThrowsIllegalArgumentException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> MoneyUtils.parseMoneyAmount(INVALID_BUDGET_STRING));
        assertTrue(exception.getMessage().contains(EXPECTED_INVALID_FORMAT_MESSAGE));
    }

    @Test
    void givenBudgetStringWithLineBreaks_whenParseMoneyAmount_thenTrimsAndReturnsCorrectMoneyAmount() {
        MoneyAmount moneyAmount = MoneyUtils.parseMoneyAmount(VALID_BUDGET_WITH_LINE_BREAKS);

        assertEquals(EXPECTED_VALID_AMOUNT, moneyAmount.getAmount());
    }
}
