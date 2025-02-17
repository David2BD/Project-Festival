package ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling;

import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RollercoasterStrategy implements ArtistSchedulingStrategy {

    public Map<LocalDate, Artist> schedule(List<Artist> artistList, LocalDate festivalStartDate) {
        Map<LocalDate, Artist> schedule = new HashMap<>();
        artistList.sort(Comparator.comparingInt(Artist::getPopularity));
        scheduleFirstAndLastDay(schedule, artistList, festivalStartDate);
        scheduleRemainingDays(schedule, artistList, festivalStartDate);
        return schedule;
    }

    private void scheduleFirstAndLastDay(
            Map<LocalDate, Artist> schedule, List<Artist> artistList, LocalDate festivalStartDate
    ) {
        int n = artistList.size();
        schedule.put(festivalStartDate, artistList.get(0));
        schedule.put(festivalStartDate.plusDays(n - 1), artistList.get(1));
    }

    private void scheduleRemainingDays(
            Map<LocalDate, Artist> schedule, List<Artist> artistList, LocalDate festivalStartDate
    ) {
        int n = artistList.size();
        for (int i = 2; i < n; i++) {
            LocalDate date = festivalStartDate.plusDays(i - 1);
            schedule.put(date, artistList.get(i));
        }
    }

}
