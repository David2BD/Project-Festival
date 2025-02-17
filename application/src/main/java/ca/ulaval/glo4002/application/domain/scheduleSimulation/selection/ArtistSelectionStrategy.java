package ca.ulaval.glo4002.application.domain.scheduleSimulation.selection;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;

import java.util.List;

public interface ArtistSelectionStrategy {
    List<Artist> select(List<Artist> artistList, int numberOfArtistsToSelect, MoneyAmount headlinerBudget, int headlinerNumber);
}
