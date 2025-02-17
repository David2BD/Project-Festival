package ca.ulaval.glo4002.application.domain.oxygen;

import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.pass.PassOxygenSelector;
import ca.ulaval.glo4002.application.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OxygenRequesterTest {

    public static final LocalDate DEADLINE_DATE = LocalDate.now().minusDays(1);
    public static final LocalDateTime REQUEST_DATE = LocalDateTime.now().minusDays(3);
    public static final int EXPECTED_DAYS = DateUtils.numberOfDaysBetween(REQUEST_DATE.toLocalDate(), DEADLINE_DATE) +
                                            1;

    private OxygenRequester oxygenRequester;
    @Mock
    private PassOxygenSelector passOxygenSelectorMock;

    @BeforeEach
    void setUp() {
        oxygenRequester = new OxygenRequester(passOxygenSelectorMock) {
            @Override
            public OxygenGrade getBaseOxygenGrade() {
                return OxygenGrade.A;
            }

            @Override
            public int calculateTanksNeeded(FestivalDates festivalDates) {
                return 10;
            }
        };

        oxygenRequester.setRequestDate(REQUEST_DATE);
    }

    @Test
    void givenOxygenRequester_whenListCompatibleOxygenGrades_thenPassOxygenSelectorMethodCalledListCompatibleOxygenGrades() {
        oxygenRequester.listCompatibleOxygenGrades(DEADLINE_DATE);

        verify(passOxygenSelectorMock).listCompatibleOxygenGrades(OxygenGrade.A, EXPECTED_DAYS);
    }

    @Test
    void givenOxygenRequester_whenFindGradeToProduce_thenPassOxygenSelectorMethodCalledDefineSupplyGrade() {
        oxygenRequester.findGradeToProduce(DEADLINE_DATE);

        verify(passOxygenSelectorMock).defineSupplyGrade(OxygenGrade.A, EXPECTED_DAYS);
    }
}
