package ca.ulaval.glo4002.application.domain.oxygen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OxygenTankProducerTest {

    private final int ANY_NUMBER_OF_TANK_PER_BUNDLE = 4;
    private final OxygenGrade ANY_OXYGEN_GRADE_TO_SUPPLY = OxygenGrade.B;
    private final OxygenTankProducer oxygenTankProducer = new OxygenTankProducer();

    @Test
    void givenTanksToSupplyLessThanTanksPerBundle_whenCalculateNumberOfTanksSupplied_thenReturnNumberOfTanksPerBundle() {
        int numberOfTanksToSupply = ANY_NUMBER_OF_TANK_PER_BUNDLE - 1;

        int tanksSupplied =
                oxygenTankProducer.calculateNumberOfTanksProduced(numberOfTanksToSupply, ANY_OXYGEN_GRADE_TO_SUPPLY);

        assertEquals(ANY_NUMBER_OF_TANK_PER_BUNDLE, tanksSupplied);
    }

    @Test
    void givenTanksToSupplyEqualToTanksPerBundle_whenCalculateNumberOfTanksSupplied_thenReturnNumberOfTanksPerBundle() {
        int tanksSupplied = oxygenTankProducer.calculateNumberOfTanksProduced(ANY_NUMBER_OF_TANK_PER_BUNDLE,
                                                                              ANY_OXYGEN_GRADE_TO_SUPPLY);

        assertEquals(ANY_NUMBER_OF_TANK_PER_BUNDLE, tanksSupplied);
    }

    @Test
    void givenTanksToSupplyTwiceGreaterThanTanksPerBundle_whenCalculateNumberOfTanksSupplied_thenReturnTwiceNumberOfTanksPerBundle() {
        int numberOfTanksToSupply = 2 * ANY_NUMBER_OF_TANK_PER_BUNDLE;

        int tanksSupplied =
                oxygenTankProducer.calculateNumberOfTanksProduced(numberOfTanksToSupply, ANY_OXYGEN_GRADE_TO_SUPPLY);

        assertEquals(2 * ANY_NUMBER_OF_TANK_PER_BUNDLE, tanksSupplied);
    }
}
