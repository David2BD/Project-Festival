package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.ConfirmedProgram;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.SchedulingType;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.selection.SelectionCriteria;
import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.ProgramConfirmRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ProgramConfirmResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ScheduleResponseDTO;
import ca.ulaval.glo4002.application.services.requests.ProgramConfirmRequest;
import ca.ulaval.glo4002.application.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgramMapperTest {
    private static final String ANY_VALID_CONFIRMATION_DATE = "2060-05-21";
    private static final String ANY_CRITERIA = "MinimizeCost";
    private static final String ANY_SCHEDULING_TYPE = "Rollercoaster";
    private static final float ANY_HEADLINER_BUDGET = 2000000;
    private static final int ANY_HEADLINER_LIMIT = 3;

    @Mock
    private ProgramConfirmRequestDTO programConfirmRequestDTOMock;
    @InjectMocks
    private ProgramMapper programMapper;

    @BeforeEach
    void setUp() {
        lenient().when(programConfirmRequestDTOMock.confirmationDate()).thenReturn(ANY_VALID_CONFIRMATION_DATE);
        lenient().when(programConfirmRequestDTOMock.criteria()).thenReturn(ANY_CRITERIA);
        lenient().when(programConfirmRequestDTOMock.scheduling()).thenReturn(ANY_SCHEDULING_TYPE);
        lenient().when(programConfirmRequestDTOMock.headlinerBudget()).thenReturn(ANY_HEADLINER_BUDGET);
        lenient().when(programConfirmRequestDTOMock.headlinerLimit()).thenReturn(ANY_HEADLINER_LIMIT);
    }

    @Test
    void givenValidProgramConfirmRequestDTO_whenMapped_thenReturnProgramConfirmRequestWithSameAttributes() {
        LocalDate expectedDate = DateUtils.parseToLocalDate(ANY_VALID_CONFIRMATION_DATE);
        SelectionCriteria expectedSelectionCriteria = SelectionCriteria.fromString(ANY_CRITERIA);
        SchedulingType expectedSchedulingType = SchedulingType.valueOf(ANY_SCHEDULING_TYPE.toUpperCase());
        MoneyAmount expectedHeadlinerBudget = new MoneyAmount(ANY_HEADLINER_BUDGET);

        ProgramConfirmRequest programConfirmRequest =
                programMapper.toProgramConfirmRequest(programConfirmRequestDTOMock);

        assertEquals(expectedDate, programConfirmRequest.getConfirmationDate());
        assertEquals(expectedSelectionCriteria, programConfirmRequest.getCriteria());
        assertEquals(expectedSchedulingType, programConfirmRequest.getSchedulingType());
        assertEquals(expectedHeadlinerBudget, programConfirmRequest.getHeadlinerBudget());
        assertEquals(ANY_HEADLINER_LIMIT, programConfirmRequest.getHeadlinerLimit());
    }

    @Test
    void givenScheduleSimulationAndTotalArtistCost_whenMapped_thenReturnsCorrectProgramConfirmResponseDTO() {
        LocalDate performanceDate1 = LocalDate.of(2060, 7, 17);
        LocalDate performanceDate2 = LocalDate.of(2060, 7, 18);
        Artist artist1 = mock(Artist.class);
        Artist artist2 = mock(Artist.class);
        when(artist1.getName()).thenReturn("Artist One");
        when(artist2.getName()).thenReturn("Artist Two");
        MoneyAmount totalArtistCost = new MoneyAmount(800.00f);
        MoneyAmount totalCost = new MoneyAmount(2000.75f);
        ConfirmedProgram confirmedProgram =
                new ConfirmedProgram(Map.of(performanceDate1, artist1, performanceDate2, artist2), totalArtistCost,
                                     totalCost);

        ProgramConfirmResponseDTO responseDTO = programMapper.toProgramConfirmResponseDTO(confirmedProgram);

        List<ScheduleResponseDTO> expectedSchedule =
                List.of(new ScheduleResponseDTO(DateUtils.formatLocalDate(performanceDate1), "Artist One"),
                        new ScheduleResponseDTO(DateUtils.formatLocalDate(performanceDate2), "Artist Two"));
        assertEquals(expectedSchedule, responseDTO.schedule());
        assertEquals(totalArtistCost.twoDecimals(), responseDTO.totalArtistCost());
        assertEquals(totalCost.twoDecimals(), responseDTO.totalCost());
    }
}