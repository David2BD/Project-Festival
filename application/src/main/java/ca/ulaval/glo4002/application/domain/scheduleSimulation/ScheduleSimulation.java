package ca.ulaval.glo4002.application.domain.scheduleSimulation;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;

import java.time.LocalDate;
import java.util.Map;

public class ScheduleSimulation {
    private final Map<LocalDate, Artist> schedule;
    private final MoneyAmount totalCostForArtists;

    public ScheduleSimulation(Map<LocalDate, Artist> schedule) {
        this.schedule = schedule;
        this.totalCostForArtists = calculateCostForArtist(schedule);
    }

    public Map<LocalDate, Artist> getSchedule() {
        return schedule;
    }

    public MoneyAmount getTotalCostForArtists() {
        return totalCostForArtists;
    }

    private MoneyAmount calculateCostForArtist(Map<LocalDate, Artist> schedule) {
        MoneyAmount artistCost = new MoneyAmount();
        for (Artist artist : schedule.values()) {
            artistCost = artistCost.add(artist.getCost());
        }
        return artistCost;
    }

    public MoneyAmount calculateCostForArtistsTransports() {
        MoneyAmount totalPrice = new MoneyAmount();
        for (Artist artist : schedule.values()) {
            ShuttleType shuttleType = artist.getShuttleType();
            totalPrice = totalPrice.add(shuttleType.getPrice().multiply(2));
        }
        return totalPrice;
    }

}
