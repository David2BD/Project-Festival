package ca.ulaval.glo4002.application.domain.scheduleSimulation.selection;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HeadlinerNumberStrategy implements ArtistSelectionStrategy {
    public List<Artist> select(
            List<Artist> artistList, int numberOfArtistsToSelect, MoneyAmount headlinerBudget, int headlinerNumber
    ) {
        List<Artist> headlinerArtistList = selectHeadlinerArtists(artistList, headlinerNumber);
        List<Artist> selectedArtists = new ArrayList<>(headlinerArtistList);
        List<Artist> remainingArtists = getArtistsSortedByCost(artistList, headlinerArtistList,
                                                               numberOfArtistsToSelect - selectedArtists.size());
        selectedArtists.addAll(remainingArtists);

        return selectedArtists;
    }

    private List<Artist> selectHeadlinerArtists(List<Artist> artistList, int headlinerNumber) {
        return artistList.stream().sorted(Comparator.comparingInt(Artist::getPopularity)).limit(headlinerNumber)
                .distinct().toList();
    }

    private List<Artist> filterUnselectedArtists(List<Artist> artistList, List<Artist> selectedArtists) {
        return artistList.stream().filter(artist -> ! selectedArtists.contains(artist)).collect(Collectors.toList());
    }

    public List<Artist> sortArtistsByCost(List<Artist> artists, int remainingCount) {
        return artists.stream()
                .sorted(Comparator.comparing(Artist::getCostValue).thenComparingInt(Artist::getPopularity))
                .limit(remainingCount).collect(Collectors.toList());
    }

    private List<Artist> getArtistsSortedByCost(
            List<Artist> artistList, List<Artist> selectedArtists, int remainingCount
    ) {
        if (remainingCount <= 0) {
            return Collections.emptyList();
        }
        List<Artist> unselectedArtists = filterUnselectedArtists(artistList, selectedArtists);

        return sortArtistsByCost(unselectedArtists, remainingCount);

    }
}
