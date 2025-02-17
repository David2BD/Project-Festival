package ca.ulaval.glo4002.application.domain.pass;

import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class PassOxygenSelectorTest {
    private static final int ANY_NUMBER_OF_DAYS_BEFORE_FESTIVAL = 10;
    private static final List<OxygenGrade> ANY_OXYGEN_GRADE_LIST = List.of(OxygenGrade.A);

    MockedStatic<OxygenGrade> mockedStaticOxygenGrade;

    private PassOxygenSelector oxygenSelector;

    @Mock
    OxygenGrade oxygenBaseGradeMock;

    @Mock
    OxygenGrade oxygenSupplyGradeMock;

    @BeforeEach
    public void setUp() {
        mockedStaticOxygenGrade = mockStatic(OxygenGrade.class);
        mockedStaticOxygenGrade.when(
                        () -> OxygenGrade.determineSupplyGrade(eq(oxygenBaseGradeMock), eq(ANY_NUMBER_OF_DAYS_BEFORE_FESTIVAL)))
                .thenReturn(oxygenSupplyGradeMock);
        mockedStaticOxygenGrade.when(
                        () -> OxygenGrade.listGradesFromTo(eq(oxygenBaseGradeMock), eq(oxygenSupplyGradeMock)))
                .thenReturn(ANY_OXYGEN_GRADE_LIST);

        oxygenSelector = new PassOxygenSelector();
    }

    @AfterEach
    void tearDown() {
        mockedStaticOxygenGrade.close();
    }

    @Test
    void givenAnyOxygenBaseGradeAndDaysBeforeFestival_whenDefineSupplyGrade_thenCallsDetermineSupplyGrade() {
        OxygenGrade result = oxygenSelector.defineSupplyGrade(oxygenBaseGradeMock, ANY_NUMBER_OF_DAYS_BEFORE_FESTIVAL);

        assertEquals(oxygenSupplyGradeMock, result);
        mockedStaticOxygenGrade.verify(() -> OxygenGrade.determineSupplyGrade(eq(oxygenBaseGradeMock),
                                                                              eq(ANY_NUMBER_OF_DAYS_BEFORE_FESTIVAL)));
    }

    @Test
    void givenAnyOxygenBaseGradeAndDaysBeforeFestival_whenListCompatibleOxygenGrades_thenCallsListGradesFromTo() {
        oxygenSelector.listCompatibleOxygenGrades(oxygenBaseGradeMock, ANY_NUMBER_OF_DAYS_BEFORE_FESTIVAL);

        mockedStaticOxygenGrade.verify(
                () -> OxygenGrade.listGradesFromTo(eq(oxygenBaseGradeMock), eq(oxygenSupplyGradeMock)));
    }
}