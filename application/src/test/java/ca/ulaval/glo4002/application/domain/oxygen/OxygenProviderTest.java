package ca.ulaval.glo4002.application.domain.oxygen;

import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OxygenProviderTest {
    private static final int TANKS_NEEDED = 5;
    private static final int TANKS_PRODUCED = 4;
    private final List<OxygenGrade> gradesToRetrieve = new ArrayList<>();
    private static final OxygenGrade ANY_GRADE_TO_PRODUCE = OxygenGrade.A;

    private final List<OxygenRequester> oxygenRequesters = new ArrayList<>();
    private static final LocalDate FESTIVAL_START_DATE = LocalDate.of(2060, 7, 17);
    private static final LocalDate FESTIVAL_END_DATE = LocalDate.of(2060, 7, 24);
    private static final FestivalDates festivalDates = new FestivalDates(FESTIVAL_START_DATE, FESTIVAL_END_DATE);
    private static final LocalDateTime PREPARATION_DATE = FESTIVAL_START_DATE.atStartOfDay().minusDays(20);

    @Mock
    OxygenRequester oxygenRequesterMock;

    @Mock
    OxygenTankProducer oxygenTankProducerMock;

    OxygenProvider oxygenProvider;

    @BeforeEach
    void setUp() {
        oxygenProvider = new OxygenProvider();
        oxygenProvider.oxygenTankProducer = oxygenTankProducerMock;
        gradesToRetrieve.add(OxygenGrade.A);
        oxygenRequesters.add(oxygenRequesterMock);

        when(oxygenRequesterMock.getRequestDate()).thenReturn(PREPARATION_DATE);
        when(oxygenRequesterMock.calculateTanksNeeded(festivalDates)).thenReturn(TANKS_NEEDED);
        when(oxygenRequesterMock.listCompatibleOxygenGrades(festivalDates.getStartDate())).thenReturn(gradesToRetrieve);
    }

    @Test
    void givenAnEmptyInventory_whenSupplyOxygenTank_thenOxygenProducerIsCalled() {
        when(oxygenRequesterMock.findGradeToProduce(festivalDates.getStartDate())).thenReturn(ANY_GRADE_TO_PRODUCE);
        when(oxygenTankProducerMock.calculateNumberOfTanksProduced(anyInt(), any(OxygenGrade.class))).thenReturn(
                TANKS_PRODUCED);

        oxygenProvider.supplyOxygen(oxygenRequesters, festivalDates);

        verify(oxygenTankProducerMock).calculateNumberOfTanksProduced(TANKS_NEEDED, ANY_GRADE_TO_PRODUCE);
    }

    @Test
    void givenAnInventory_whenSupplyOxygenTank_thenLogsAdded() {
        when(oxygenRequesterMock.findGradeToProduce(festivalDates.getStartDate())).thenReturn(ANY_GRADE_TO_PRODUCE);
        when(oxygenTankProducerMock.calculateNumberOfTanksProduced(anyInt(), any(OxygenGrade.class))).thenReturn(
                TANKS_NEEDED);

        oxygenProvider.supplyOxygen(oxygenRequesters, festivalDates);

        assertFalse(oxygenProvider.getOxygenLogEntries().isEmpty());
    }

    @Test
    void givenGradeAToProduce_whenSupplyOxygenTank_thenGradeAAddedToInventory() {
        when(oxygenRequesterMock.findGradeToProduce(festivalDates.getStartDate())).thenReturn(OxygenGrade.A);
        when(oxygenTankProducerMock.calculateNumberOfTanksProduced(TANKS_NEEDED, ANY_GRADE_TO_PRODUCE)).thenReturn(
                TANKS_PRODUCED);

        oxygenProvider.supplyOxygen(oxygenRequesters, festivalDates);

        assertNotNull(oxygenProvider.getOxygenInventory().get(OxygenGrade.A));
    }

    @Test
    void givenGradeBToProduce_whenSupplyOxygenTank_thenGradeBAddedToInventory() {
        when(oxygenRequesterMock.findGradeToProduce(festivalDates.getStartDate())).thenReturn(OxygenGrade.B);
        when(oxygenTankProducerMock.calculateNumberOfTanksProduced(TANKS_NEEDED, OxygenGrade.B)).thenReturn(
                TANKS_PRODUCED);

        oxygenProvider.supplyOxygen(oxygenRequesters, festivalDates);

        assertNotNull(oxygenProvider.getOxygenInventory().get(OxygenGrade.B));
    }

    @Test
    void givenGradeEToProduce_whenSupplyOxygenTank_thenGradeERemainsNullInInventory() {
        when(oxygenRequesterMock.findGradeToProduce(festivalDates.getStartDate())).thenReturn(OxygenGrade.E);
        when(oxygenTankProducerMock.calculateNumberOfTanksProduced(TANKS_NEEDED, OxygenGrade.E)).thenReturn(
                TANKS_PRODUCED);

        oxygenProvider.supplyOxygen(oxygenRequesters, festivalDates);

        assertEquals(0, oxygenProvider.getOxygenInventory().get(OxygenGrade.E));
    }
}
