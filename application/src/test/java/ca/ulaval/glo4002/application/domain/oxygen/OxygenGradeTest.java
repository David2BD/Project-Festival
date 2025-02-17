package ca.ulaval.glo4002.application.domain.oxygen;

import ca.ulaval.glo4002.application.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class OxygenGradeTest {
    private final OxygenGrade ANY_OXYGEN_GRADE = OxygenGrade.B;
    private final OxygenGrade HIGHER_OXYGEN_GRADE = OxygenGrade.E;
    private final int LESS_THAN_PRODUCTION_TIME = 1;
    private final int MORE_THAN_PRODUCTION_TIME = 50;

    @Test
    void givenBaseGradeA_whenGetGradesSortedFromBaseToHighest_thenReturnGradesABE() {
        OxygenGrade grade = OxygenGrade.A;

        List<OxygenGrade> grades = grade.getGradesSortedFromBaseToHighest();

        assertEquals(OxygenGrade.A, grades.get(0));
        assertEquals(OxygenGrade.B, grades.get(1));
        assertEquals(OxygenGrade.E, grades.get(2));
    }

    @Test
    void givenBaseGradeB_whenGetGradesSortedFromBaseToHighest_thenReturnGradesBE() {
        OxygenGrade grade = OxygenGrade.B;

        List<OxygenGrade> grades = grade.getGradesSortedFromBaseToHighest();

        assertEquals(OxygenGrade.B, grades.get(0));
        assertEquals(OxygenGrade.E, grades.get(1));
    }

    @Test
    void givenBaseGradeE_whenGetGradesSortedFromBaseToHighest_thenReturnGradeE() {
        OxygenGrade grade = OxygenGrade.E;

        List<OxygenGrade> grades = grade.getGradesSortedFromBaseToHighest();

        assertEquals(OxygenGrade.E, grades.get(0));
    }

    @Test
    void givenGradeAAndGradeB_whenListGradesFromTo_thenReturnGradesAB() {
        List<OxygenGrade> grades = OxygenGrade.listGradesFromTo(OxygenGrade.A, OxygenGrade.B);

        assertEquals(OxygenGrade.A, grades.get(0));
        assertEquals(OxygenGrade.B, grades.get(1));
    }

    @Test
    void givenGradeBAndGradeA_whenListGradesFromTo_thenReturnGradesAB() {
        List<OxygenGrade> grades = OxygenGrade.listGradesFromTo(OxygenGrade.B, OxygenGrade.A);

        assertEquals(OxygenGrade.A, grades.get(0));
        assertEquals(OxygenGrade.B, grades.get(1));
    }

    @Test
    void givenGradeAAndGradeE_whenListGradesFromTo_thenReturnGradesABE() {
        List<OxygenGrade> grades = OxygenGrade.listGradesFromTo(OxygenGrade.A, OxygenGrade.E);

        assertEquals(OxygenGrade.A, grades.get(0));
        assertEquals(OxygenGrade.B, grades.get(1));
        assertEquals(OxygenGrade.E, grades.get(2));
    }

    @Test
    void givenGrade_whenProductionTimeShorterThanTimeLeftBeforeFestival_thenReturnSameSupplyGrade() {
        OxygenGrade determinedSupplyGrade =
                OxygenGrade.determineSupplyGrade(ANY_OXYGEN_GRADE, MORE_THAN_PRODUCTION_TIME);

        assertEquals(ANY_OXYGEN_GRADE, determinedSupplyGrade);
    }

    @Test
    void givenGrade_whenProductionTimeLongerThanTimeLeftBeforeFestival_thenReturnHigherSupplyGrade() {
        OxygenGrade determinedSupplyGrade =
                OxygenGrade.determineSupplyGrade(ANY_OXYGEN_GRADE, LESS_THAN_PRODUCTION_TIME);

        assertEquals(HIGHER_OXYGEN_GRADE, determinedSupplyGrade);
    }

    @Test
    void givenGrade_whenTimeLeftIsZero_thenReturnE() {
        OxygenGrade determinedSupplyGrade = OxygenGrade.determineSupplyGrade(ANY_OXYGEN_GRADE, 0);

        assertEquals(OxygenGrade.E, determinedSupplyGrade);
    }

    @Test
    void givenPreparationOrderDate_whenDetermineProductionTimestamp_thenCallDateUtilsCalculateTimestamp() {
        LocalDateTime ANY_PREPARATION_ORDER_DATE = mock(LocalDateTime.class);
        MockedStatic<DateUtils> DateUtilsMock = mockStatic(DateUtils.class);

        ANY_OXYGEN_GRADE.determineProductionTimestamp(ANY_PREPARATION_ORDER_DATE);

        DateUtilsMock.verify(
                () -> DateUtils.calculateTimestamp(ANY_PREPARATION_ORDER_DATE, ANY_OXYGEN_GRADE.getProductionTime()));
        DateUtilsMock.close();
    }
}
