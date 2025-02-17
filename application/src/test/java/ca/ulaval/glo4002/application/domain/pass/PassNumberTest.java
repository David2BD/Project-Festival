package ca.ulaval.glo4002.application.domain.pass;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PassNumberTest {
    private static final String PASS_NUMBER = "12345L";
    private PassNumber passNumber1;
    private PassNumber passNumber2;

    @Test
    void whenGenerateNumberForPass_thenNumbersAreNotEquals() {
        passNumber1 = PassNumber.generateNewPassNumber();
        passNumber2 = PassNumber.generateNewPassNumber();

        assertNotEquals(passNumber1, passNumber2);
    }

    @Test
    void givenTwoPassNumberInstancesWithSameValue_whenCompare_thenInstancesAreEqual() {
        passNumber1 = new PassNumber(PASS_NUMBER);
        passNumber2 = new PassNumber(PASS_NUMBER);

        assertEquals(passNumber1, passNumber2);
    }

    @Test
    void givenTwoPassNumberInstancesWithSameValue_whenHashCode_thenHashCodesAreEqual() {
        passNumber1 = new PassNumber(PASS_NUMBER);
        passNumber2 = new PassNumber(PASS_NUMBER);

        assertEquals(passNumber1.hashCode(), passNumber2.hashCode());
    }

}
