package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.ScheduleSimulation;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ScheduleEntryResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.SimulationReportResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimulationReportMapperTest {
    private static final LocalDate TEST_DATE = LocalDate.of(2024, 11, 24);
    private static final String ARTIST_NAME = "Mock Artist";
    private static final BigDecimal ARTIST_COST = new BigDecimal("1234.56");

    @InjectMocks
    private SimulationReportMapper mapper;

    @Mock
    Artist artistMock;

    @Mock
    MoneyAmount moneyAmountMock;

    @Mock
    ScheduleSimulation scheduleSimulationMock;

    @BeforeEach
    void setUp() {
        mapper = new SimulationReportMapper();
    }

    @Test
    void givenScheduleSimulation_whenToDto_thenMapsCorrectly() {
        when(artistMock.getName()).thenReturn(ARTIST_NAME);
        when(moneyAmountMock.twoDecimals()).thenReturn(ARTIST_COST);
        when(scheduleSimulationMock.getSchedule()).thenReturn(Map.of(TEST_DATE, artistMock));
        when(scheduleSimulationMock.getTotalCostForArtists()).thenReturn(moneyAmountMock);

        SimulationReportResponseDTO responseDTO = mapper.toDto(scheduleSimulationMock);

        assertEquals(1, responseDTO.getSchedule().size());
        ScheduleEntryResponseDTO scheduleEntry = responseDTO.getSchedule().get(0);
        assertEquals(TEST_DATE.toString(), scheduleEntry.getDate());
        assertEquals(ARTIST_NAME, scheduleEntry.getArtist());
        assertEquals(ARTIST_COST, responseDTO.getTotalArtistCost());
        verify(artistMock).getName();
        verify(scheduleSimulationMock).getSchedule();
        verify(scheduleSimulationMock).getTotalCostForArtists();
        verify(moneyAmountMock).twoDecimals();
    }
}
