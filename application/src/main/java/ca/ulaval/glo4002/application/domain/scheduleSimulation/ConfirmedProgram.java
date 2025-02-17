package ca.ulaval.glo4002.application.domain.scheduleSimulation;

import ca.ulaval.glo4002.application.domain.MoneyAmount;

import java.time.LocalDate;
import java.util.Map;

public class ConfirmedProgram {
    private final Map<LocalDate, Artist> schedule;
    private final MoneyAmount totalCostForArtists;
    private final MoneyAmount totalCost;

    public Map<LocalDate, Artist> getSchedule() {
        return schedule;
    }

    public ConfirmedProgram(
            Map<LocalDate, Artist> schedule, MoneyAmount totalCostForArtists, MoneyAmount totalCost
    ) {
        this.schedule = schedule;
        this.totalCostForArtists = totalCostForArtists;
        this.totalCost = totalCost;
    }

    public MoneyAmount getTotalCostForArtists() {
        return totalCostForArtists;
    }

    public MoneyAmount getTotalCost() {
        return totalCost;
    }
}
