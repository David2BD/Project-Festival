package ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling;

import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrescendoStrategy implements ArtistSchedulingStrategy {
    public Map<LocalDate, Artist> schedule(List<Artist> artistList, LocalDate festivalStartDate) {
        artistList.sort(Comparator.comparingInt(Artist::getPopularity).reversed());
        Map<LocalDate, Artist> schedule = new HashMap<>();

        for (int i = 0; i < artistList.size(); i++) {
            LocalDate date = festivalStartDate.plusDays(i);
            schedule.put(date, artistList.get(i));
        }

        return schedule;
    }
}
