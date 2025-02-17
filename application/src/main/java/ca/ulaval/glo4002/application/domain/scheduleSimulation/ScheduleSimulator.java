package ca.ulaval.glo4002.application.domain.scheduleSimulation;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.ArtistSchedulingStrategy;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.selection.ArtistSelectionStrategy;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ScheduleSimulator {
    private final FestivalDates festivalDates;

    public ScheduleSimulator(FestivalDates festivalDates) {
        this.festivalDates = festivalDates;
    }

    public ScheduleSimulation simulateSchedule(
            List<Artist> allArtists, ArtistSelectionStrategy selectionStrategy,
            ArtistSchedulingStrategy schedulingStrategy, MoneyAmount headlinerBudget, int headlinerLimit
    ) {
        List<Artist> selectedArtists = selectArtists(allArtists, selectionStrategy, headlinerBudget, headlinerLimit);
        Map<LocalDate, Artist> schedule = scheduleArtists(selectedArtists, schedulingStrategy);

        return new ScheduleSimulation(schedule);
    }

    private List<Artist> selectArtists(
            List<Artist> allArtists, ArtistSelectionStrategy selectionStrategy, MoneyAmount headlinerBudget,
            int headlinerLimit
    ) {
        int qtyToSelect = festivalDates.getFestivalDurationInDays();
        return selectionStrategy.select(allArtists, qtyToSelect, headlinerBudget, headlinerLimit);
    }

    private Map<LocalDate, Artist> scheduleArtists(
            List<Artist> selectedArtists, ArtistSchedulingStrategy schedulingStrategy
    ) {
        return schedulingStrategy.schedule(selectedArtists, festivalDates.getStartDate());
    }

}
