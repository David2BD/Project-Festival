package ca.ulaval.glo4002.application.domain;

import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;

import java.util.List;

public interface ArtistsListSource {
    List<Artist> extractAvailableArtists();
}
