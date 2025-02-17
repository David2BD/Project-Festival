package ca.ulaval.glo4002.application.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MoneyAmountTest {
    private final int ANY_INT = 12;
    private final MoneyAmount ANY_MONEYAMOUNT = new MoneyAmount(12.345);

    private final MoneyAmount A_MONEYAMOUNT = new MoneyAmount(25);
    private final MoneyAmount AN_OTHER_MONEYAMOUNT = new MoneyAmount(10);

    @Test
    void whenCreatingNewMoneyAmount_thenAmountIsZero() {
        MoneyAmount moneyAmount = new MoneyAmount();

        BigDecimal expectedValue = BigDecimal.ZERO;
        assertEquals(expectedValue, moneyAmount.getAmount());
    }

    @Test
    void whenCreatingNewMoneyAmountFromBigDecimal_thenAmountIsValueOfBigDecimal() {
        final BigDecimal ANY_BIG_DECIMAL = BigDecimal.valueOf(12.345);
        MoneyAmount moneyAmount = new MoneyAmount(ANY_BIG_DECIMAL);

        BigDecimal expectedValue = BigDecimal.valueOf(12.345);
        assertEquals(expectedValue, moneyAmount.getAmount());
    }

    @Test
    void whenCreatingNewMoneyAmountFromInt_thenAmountIsValueOfInt() {
        MoneyAmount moneyAmount = new MoneyAmount(ANY_INT);

        BigDecimal expectedValue = BigDecimal.valueOf(12);
        assertEquals(expectedValue, moneyAmount.getAmount());
    }

    @Test
    void whenCreatingNewMoneyAmountFromFloat_thenAmountIsValueOfFloat() {
        final float ANY_FLOAT = 12.345f;
        MoneyAmount moneyAmount = new MoneyAmount(ANY_FLOAT);

        BigDecimal expectedValue = BigDecimal.valueOf(12.345f);
        assertEquals(expectedValue, moneyAmount.getAmount());
    }

    @Test
    void whenCreatingNewMoneyAmountFromDouble_thenAmountIsValueOfDouble() {
        final double ANY_DOUBLE = 12.345;
        MoneyAmount moneyAmount = new MoneyAmount(ANY_DOUBLE);

        BigDecimal expectedValue = BigDecimal.valueOf(12.345);
        assertEquals(expectedValue, moneyAmount.getAmount());
    }

    @Test
    void whenCreatingNewMoneyAmountFromMoneyAmount_thenAmountIsValueOfMoneyAmount() {
        MoneyAmount moneyAmount = new MoneyAmount(ANY_MONEYAMOUNT);

        BigDecimal expectedValue = BigDecimal.valueOf(12.345);
        assertEquals(expectedValue, moneyAmount.getAmount());
    }

    @Test
    void givenMoneyAmountWithAmountOfZeroDecimal_whenCallingTwoDecimals_thenReturnValueWithTwoDecimals() {
        final BigDecimal BIG_DECIMAL_WITH_ZERO_DECIMAL = BigDecimal.valueOf(12);
        MoneyAmount moneyAmount = new MoneyAmount(BIG_DECIMAL_WITH_ZERO_DECIMAL);

        BigDecimal returnedValue = moneyAmount.twoDecimals();

        assertEquals(2, returnedValue.scale());
    }

    @Test
    void givenMoneyAmountWithAmountOfOneDecimal_whenCallingTwoDecimals_thenReturnValueWithTwoDecimals() {
        final BigDecimal BIG_DECIMAL_WITH_ONE_DECIMAL = BigDecimal.valueOf(12.3);
        MoneyAmount moneyAmount = new MoneyAmount(BIG_DECIMAL_WITH_ONE_DECIMAL);

        BigDecimal returnedValue = moneyAmount.twoDecimals();

        assertEquals(2, returnedValue.scale());
    }

    @Test
    void givenMoneyAmountWithAmountOfTwoDecimal_whenCallingTwoDecimals_thenReturnValueWithTwoDecimals() {
        final BigDecimal BIG_DECIMAL_WITH_TWO_DECIMAL = BigDecimal.valueOf(12.34);
        MoneyAmount moneyAmount = new MoneyAmount(BIG_DECIMAL_WITH_TWO_DECIMAL);

        BigDecimal returnedValue = moneyAmount.twoDecimals();

        assertEquals(2, returnedValue.scale());
    }

    @Test
    void givenMoneyAmountWithAmountOfThreeDecimal_whenCallingTwoDecimals_thenReturnValueWithTwoDecimals() {
        final BigDecimal BIG_DECIMAL_WITH_MORE_THEN_TWO_DECIMAL = BigDecimal.valueOf(12.3456);
        MoneyAmount moneyAmount = new MoneyAmount(BIG_DECIMAL_WITH_MORE_THEN_TWO_DECIMAL);

        BigDecimal returnedValue = moneyAmount.twoDecimals();

        BigDecimal expectedValue = BigDecimal.valueOf(12.35);
        assertEquals(2, returnedValue.scale());
        assertEquals(expectedValue, returnedValue);
    }

    @Test
    void givenAnyMoneyAmount_whenAddingMoneyAmount_thenReturnMoneyAmountThatIsSum() {
        MoneyAmount sum = A_MONEYAMOUNT.add(AN_OTHER_MONEYAMOUNT);

        final MoneyAmount SUM_OF_MONEYAMOUNTS = new MoneyAmount(35);
        assertEquals(SUM_OF_MONEYAMOUNTS.getAmount(), sum.getAmount());
    }

    @Test
    void givenAnyMoneyAmount_whenSubstractingMoneyAmount_thenReturnMoneyAmountThatIsDifference() {
        MoneyAmount difference = A_MONEYAMOUNT.subtract(AN_OTHER_MONEYAMOUNT);

        MoneyAmount DIFFERENCE_OF_MONEYAMOUNTS = new MoneyAmount(15);
        assertEquals(DIFFERENCE_OF_MONEYAMOUNTS.getAmount(), difference.getAmount());
    }

    @Test
    void givenAnyMoneyAmount_whenMultiplingByInt_thenReturnMoneyAmountThatIsProduct() {
        MoneyAmount product = ANY_MONEYAMOUNT.multiply(ANY_INT);

        final MoneyAmount PRODUCT_OF_MONEYAMOUNT_BY_INT = new MoneyAmount(BigDecimal.valueOf(148.140).setScale(3));
        assertEquals(PRODUCT_OF_MONEYAMOUNT_BY_INT.getAmount(), product.getAmount());
    }

    @Test
    void givenAnyMoneyAmount_whenDividingByInt_thenReturnMoneyAmountThatIsIntegralQuotient() {
        int integralQuotient = ANY_MONEYAMOUNT.divide(ANY_INT);

        final int INTEGRAL_QUOTIENT_OF_MONEYAMOUNT_BY_INT = 1;
        assertEquals(INTEGRAL_QUOTIENT_OF_MONEYAMOUNT_BY_INT, integralQuotient);
    }

    @Test
    void givenAnyMoneyAmount_whenGettingFloatValue_thenReturnedFloatIsSameAsAmount() {
        float floatValue = ANY_MONEYAMOUNT.floatValue();

        final float ANY_MONEYAMOUNT_FLOAT_VALUE = 12.34f;
        assertEquals(ANY_MONEYAMOUNT_FLOAT_VALUE, floatValue);
    }
}
