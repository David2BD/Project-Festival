package ca.ulaval.glo4002.application.domain.scheduleSimulation.selection;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MinimizeCostStrategy implements ArtistSelectionStrategy {
    public List<Artist> select(
            List<Artist> artistList, int numberOfArtistsToSelect, MoneyAmount headlinerBudget, int headlinerNumber
    ) {
        return artistList.stream().sorted(Comparator.comparing((Artist artist) -> artist.getCostValue())
                                                  .thenComparingInt(Artist::getPopularity))
                .limit(numberOfArtistsToSelect).distinct().collect(Collectors.toList());
    }

}
