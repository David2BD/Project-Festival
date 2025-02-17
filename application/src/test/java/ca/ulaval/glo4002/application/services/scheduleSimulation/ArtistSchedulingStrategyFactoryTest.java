package ca.ulaval.glo4002.application.services.scheduleSimulation;

import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.ArtistSchedulingStrategy;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.CrescendoStrategy;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.RollercoasterStrategy;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.SchedulingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MockitoExtension.class)
class ArtistSchedulingStrategyFactoryTest {

    private ArtistSchedulingStrategyFactory factory;

    @BeforeEach
    void setUp() {
        factory = new ArtistSchedulingStrategyFactory();
    }

    @Test
    void givenSchedulingTypeIsCrescendo_whenCreating_thenReturnCrescendoStrategyClass() {
        SchedulingType type = SchedulingType.CRESCENDO;
        ArtistSchedulingStrategy returnedInstance = factory.create(type);
        assertInstanceOf(CrescendoStrategy.class, returnedInstance);
    }

    @Test
    void givenSchedulingTypeIsRollerCoaster_whenCreating_thenReturnRollercoasterStrategyClass() {
        SchedulingType type = SchedulingType.ROLLERCOASTER;
        ArtistSchedulingStrategy returnedInstance = factory.create(type);
        assertInstanceOf(RollercoasterStrategy.class, returnedInstance);
    }

}
