package ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling;

import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ArtistSchedulingStrategy {
    Map<LocalDate, Artist> schedule(List<Artist> artistList, LocalDate festivalStartDate);
}
