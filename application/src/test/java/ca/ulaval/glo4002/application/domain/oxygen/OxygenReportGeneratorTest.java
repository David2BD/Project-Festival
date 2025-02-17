package ca.ulaval.glo4002.application.domain.oxygen;

import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.reports.OxygenReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OxygenReportGeneratorTest {
    OxygenReportGenerator oxygenReportGenerator;
    List<OxygenRequester> oxygenRequesters;

    @Mock
    OxygenProvider oxygenProviderMock;

    @Mock
    FestivalDates festivalDatesMock;

    @Mock
    OxygenRequester oxygenRequesterMock;

    @BeforeEach
    public void setUp() {
        oxygenReportGenerator = new OxygenReportGenerator(oxygenProviderMock);
        oxygenRequesters = new ArrayList<>();
        oxygenRequesters.add(oxygenRequesterMock);
    }

    @Test
    void whenGeneratingOxygenReport_thenDelegateToOxygenInventory() {
        oxygenReportGenerator.generateOxygenReport(oxygenRequesters, festivalDatesMock);

        verify(oxygenProviderMock).supplyOxygen(oxygenRequesters, festivalDatesMock);
    }

    @Test
    void whenGeneratingOxygenReport_thenReturnOxygenReport() {
        OxygenReport oxygenReport = oxygenReportGenerator.generateOxygenReport(oxygenRequesters, festivalDatesMock);

        assertNotNull(oxygenReport);
    }
}
