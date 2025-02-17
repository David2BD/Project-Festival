package ca.ulaval.glo4002.application.domain.scheduleSimulation.selection;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HeadlinerBudgetStrategy implements ArtistSelectionStrategy {
    public List<Artist> select(
            List<Artist> artistList, int numberOfArtistsToSelect, MoneyAmount headlinerBudget, int headlinerNumber
    ) {
        List<Artist> selectedArtists = new ArrayList<>();

        List<Artist> popularArtistsLists = getArtistsSortedByPopular(artistList);
        selectedArtists.addAll(selectArtistsByBudget(popularArtistsLists, headlinerBudget, numberOfArtistsToSelect));

        List<Artist> remainingArtists = getUnselectedArtistsSortedByCost(artistList, selectedArtists);
        selectedArtists.addAll(
                selectRemainingArtists(remainingArtists, numberOfArtistsToSelect - selectedArtists.size()));

        return selectedArtists;
    }

    public List<Artist> getArtistsSortedByPopular(List<Artist> artistList) {
        return artistList.stream().sorted(Comparator.comparingInt(Artist::getPopularity)).distinct().toList();
    }

    public List<Artist> getUnselectedArtistsSortedByCost(List<Artist> artistList, List<Artist> selectedArtists) {
        List<Artist> artists =
                artistList.stream().filter(artist -> ! selectedArtists.contains(artist)).collect(Collectors.toList());
        return sortArtistListByCost(artists);
    }

    public List<Artist> sortArtistListByCost(List<Artist> artistList) {
        return artistList.stream()
                .sorted(Comparator.comparing(Artist::getCostValue).thenComparingInt(Artist::getPopularity))
                .collect(Collectors.toList());
    }

    private List<Artist> selectArtistsByBudget(
            List<Artist> artists, MoneyAmount headlinerBudget, int numberOfArtistsToSelect
    ) {
        List<Artist> selected = new ArrayList<>();
        for (Artist artist : artists) {
            MoneyAmount remainingBudget = artist.subtractFromBudgetIfPossible(headlinerBudget);
            if (remainingBudget != headlinerBudget) {
                selected.add(artist);
                headlinerBudget = remainingBudget;
                if (selected.size() == numberOfArtistsToSelect) {
                    break;
                }
            }
        }
        return selected;
    }

    private List<Artist> selectRemainingArtists(List<Artist> artists, int remainingCount) {
        List<Artist> selected = new ArrayList<>();
        for (int i = 0; i < artists.size() && selected.size() < remainingCount; i++) {
            selected.add(artists.get(i));
        }
        return selected;
    }

}
