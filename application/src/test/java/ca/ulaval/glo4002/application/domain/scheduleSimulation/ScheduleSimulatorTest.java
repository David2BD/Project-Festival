package ca.ulaval.glo4002.application.domain.scheduleSimulation;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.ArtistSchedulingStrategy;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.selection.ArtistSelectionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleSimulatorTest {

    @Mock
    private FestivalDates festivalDatesMock;
    @Mock
    private ArtistSelectionStrategy selectionStrategyMock;
    @Mock
    private ArtistSchedulingStrategy schedulingStrategyMock;
    @Mock
    private Artist artist1Mock;
    @Mock
    private Artist artist2Mock;

    private final MoneyAmount headlinerBudget = new MoneyAmount(500);
    private final int headlinerLimit = 2;
    private final LocalDate startDate = LocalDate.of(2024, 11, 1);
    private final int festivalDuration = 2;

    private ScheduleSimulator scheduleSimulator;

    @BeforeEach
    void setUp() {
        when(festivalDatesMock.getStartDate()).thenReturn(startDate);
        when(festivalDatesMock.getFestivalDurationInDays()).thenReturn(festivalDuration);

        lenient().when(artist1Mock.getCost()).thenReturn(new MoneyAmount(100));
        lenient().when(artist2Mock.getCost()).thenReturn(new MoneyAmount(200));

        scheduleSimulator = new ScheduleSimulator(festivalDatesMock);
    }

    @Test
    void givenArtistsAndStrategies_whenSimulateSchedule_thenReturnsCorrectScheduleSimulation() {
        List<Artist> allArtists = List.of(artist1Mock, artist2Mock);
        List<Artist> selectedArtists = List.of(artist1Mock);
        Map<LocalDate, Artist> scheduledArtists = Map.of(startDate, artist1Mock, startDate.plusDays(1), artist2Mock);
        mockStrategyBehaviors(allArtists, selectedArtists, scheduledArtists);

        ScheduleSimulation scheduleSimulation = createSimulation(allArtists);

        verifyStrategyInteractions(allArtists, selectedArtists);
        assertEquals(scheduledArtists, scheduleSimulation.getSchedule());
    }

    @Test
    void givenNoArtists_whenSimulateSchedule_thenReturnsEmptySchedule() {
        List<Artist> allArtists = List.of();
        List<Artist> selectedArtists = List.of();
        Map<LocalDate, Artist> scheduledArtists = Map.of();
        mockStrategyBehaviors(allArtists, selectedArtists, scheduledArtists);

        ScheduleSimulation scheduleSimulation = createSimulation(allArtists);

        verifyStrategyInteractions(allArtists, selectedArtists);
        assertEquals(scheduledArtists, scheduleSimulation.getSchedule());
    }

    private void mockStrategyBehaviors(
            List<Artist> allArtists, List<Artist> selectedArtists, Map<LocalDate, Artist> scheduledArtists
    ) {
        when(selectionStrategyMock.select(allArtists, festivalDuration, headlinerBudget, headlinerLimit)).thenReturn(
                selectedArtists);
        when(schedulingStrategyMock.schedule(selectedArtists, startDate)).thenReturn(scheduledArtists);
    }

    private ScheduleSimulation createSimulation(List<Artist> allArtists) {
        return scheduleSimulator.simulateSchedule(allArtists, selectionStrategyMock, schedulingStrategyMock,
                                                  headlinerBudget, headlinerLimit);
    }

    private void verifyStrategyInteractions(
            List<Artist> allArtists, List<Artist> selectedArtists
    ) {
        verify(selectionStrategyMock).select(allArtists, festivalDuration, headlinerBudget, headlinerLimit);
        verify(schedulingStrategyMock).schedule(selectedArtists, startDate);
    }
}
