package ca.ulaval.glo4002.application.services;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.SchedulingType;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.selection.SelectionCriteria;
import ca.ulaval.glo4002.application.services.requests.ProgramConfirmRequest;
import ca.ulaval.glo4002.application.services.scheduleSimulation.ArtistSchedulingStrategyFactory;
import ca.ulaval.glo4002.application.services.scheduleSimulation.ArtistSelectionStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProgramServiceTest {
    private final SelectionCriteria ANY_SELECTION_CRITERIA = SelectionCriteria.HEADLINER_BUDGET;
    private final SchedulingType ANY_SCHEDULING_TYPE = SchedulingType.CRESCENDO;
    private final String ANY_HEADLINER_BUDGET = "1000";
    private final MoneyAmount ANY_HEADLINER_BUDGET_MONEY = new MoneyAmount(1000);
    private final Integer ANY_HEADLINER_LIMIT = 10;
    private ProgramService programService;

    @Mock
    FestivalRepository festivalRepositoryMock;

    @Mock
    Festival festivalMock;

    @Mock
    ArtistSelectionStrategyFactory selectionFactoryMock;

    @Mock
    ArtistSchedulingStrategyFactory schedulingFactoryMock;

    @Mock
    ProgramConfirmRequest programConfirmRequestMock;

    @BeforeEach
    void setUp() {
        lenient().when(programConfirmRequestMock.getCriteria()).thenReturn(ANY_SELECTION_CRITERIA);
        lenient().when(programConfirmRequestMock.getSchedulingType()).thenReturn(ANY_SCHEDULING_TYPE);
        lenient().when(programConfirmRequestMock.getHeadlinerBudget()).thenReturn(ANY_HEADLINER_BUDGET_MONEY);
        lenient().when(programConfirmRequestMock.getHeadlinerLimit()).thenReturn(ANY_HEADLINER_LIMIT);

        lenient().when(festivalRepositoryMock.getFestival()).thenReturn(festivalMock);

        programService = new ProgramService(festivalRepositoryMock, selectionFactoryMock, schedulingFactoryMock);
    }

    @Test
    void givenSelectionCriteria_whenGetSimulation_thenCallArtistSelectionStrategyFactory() {
        programService.getSimulation(ANY_SELECTION_CRITERIA, ANY_SCHEDULING_TYPE, ANY_HEADLINER_BUDGET,
                                     ANY_HEADLINER_LIMIT);

        verify(selectionFactoryMock).create(ANY_SELECTION_CRITERIA);
    }

    @Test
    void givenSchedulingType_whenGetSimulation_thenCallArtistSchedulingStrategyFactory() {
        programService.getSimulation(ANY_SELECTION_CRITERIA, ANY_SCHEDULING_TYPE, ANY_HEADLINER_BUDGET,
                                     ANY_HEADLINER_LIMIT);

        verify(schedulingFactoryMock).create(ANY_SCHEDULING_TYPE);
    }

    @Test
    void whenGetSimulation_thenCallsFestivalGetScheduleSimulation() {
        programService.getSimulation(ANY_SELECTION_CRITERIA, ANY_SCHEDULING_TYPE, ANY_HEADLINER_BUDGET,
                                     ANY_HEADLINER_LIMIT);

        verify(festivalMock).getScheduleSimulation(any(), any(), any(), any(), anyInt());
    }

    @Test
    void givenProgramConfirmRequest_whenConfirmProgram_thenCallArtistSelectionStrategyFactory() {
        programService.confirmProgram(programConfirmRequestMock);

        verify(selectionFactoryMock).create(ANY_SELECTION_CRITERIA);
    }

    @Test
    void givenProgramConfirmRequest_whenConfirmProgram_thenCallArtistSchedulingStrategyFactory() {
        programService.confirmProgram(programConfirmRequestMock);

        verify(schedulingFactoryMock).create(ANY_SCHEDULING_TYPE);
    }

    @Test
    void givenProgramConfirmRequest_whenConfirmProgram_thenCallsFestivalConfirmProgram() {
        programService.confirmProgram(programConfirmRequestMock);

        verify(festivalMock).confirmProgram(any(), any());
    }

    @Test
    void givenProgramConfirmRequest_whenConfirmProgram_thenSavesFestival() {
        programService.confirmProgram(programConfirmRequestMock);

        verify(festivalRepositoryMock).saveFestival(festivalMock);
    }
}
