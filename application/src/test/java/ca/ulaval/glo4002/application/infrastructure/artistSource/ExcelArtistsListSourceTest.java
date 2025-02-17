package ca.ulaval.glo4002.application.infrastructure.artistSource;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelArtistsListSourceTest {
    private static final int EXPECTED_AVAILABLE_ARTISTS_COUNT = 16;
    private static final List<Artist> EXPECTED_ARTISTS;

    static {
        EXPECTED_ARTISTS = new ArrayList<>();
        EXPECTED_ARTISTS.add(new Artist("Sun 41", new MoneyAmount(69000.0), 9, 5, "pop"));
        EXPECTED_ARTISTS.add(new Artist("Black Earth Peas", new MoneyAmount(200000.0), 8, 4, "pop"));
        EXPECTED_ARTISTS.add(new Artist("Mumford and Suns", new MoneyAmount(125000.0), 17, 4, "folk"));
        EXPECTED_ARTISTS.add(new Artist("Kid Rocket", new MoneyAmount(350000.0), 18, 1, "pop"));
        EXPECTED_ARTISTS.add(new Artist("Cyndi Dauppler", new MoneyAmount(25000.0), 14, 1, "pop"));
        EXPECTED_ARTISTS.add(new Artist("Kelvin Harris", new MoneyAmount(210000.0), 15, 1, "pop"));
        EXPECTED_ARTISTS.add(new Artist("Suns N’ Roses", new MoneyAmount(98000.0), 19, 7, "rock"));
        EXPECTED_ARTISTS.add(new Artist("Eclipse Presley", new MoneyAmount(321000.0), 1, 1, "rock"));
        EXPECTED_ARTISTS.add(new Artist("Coldray", new MoneyAmount(607000.0), 13, 5, "pop"));
        EXPECTED_ARTISTS.add(new Artist("Megadearth", new MoneyAmount(120000.0), 12, 4, "rock"));
        EXPECTED_ARTISTS.add(new Artist("David Glowie", new MoneyAmount(390000.0), 4, 1, "rock"));
        EXPECTED_ARTISTS.add(new Artist("XRay Charles", new MoneyAmount(110000.0), 5, 1, "jazz"));
        EXPECTED_ARTISTS.add(new Artist("Freddie Mercury", new MoneyAmount(298000.0), 3, 1, "rock"));
        EXPECTED_ARTISTS.add(new Artist("Rolling Stars", new MoneyAmount(550550.0), 6, 4, "rock"));
        EXPECTED_ARTISTS.add(new Artist("Simple Planet", new MoneyAmount(112000.0), 11, 5, "pop"));
        EXPECTED_ARTISTS.add(new Artist("Novana", new MoneyAmount(410000.0), 2, 3, "grunge"));
    }

    private ExcelArtistsListSource artistsListSource;

    @BeforeEach
    void setUp() {
        artistsListSource = new ExcelArtistsListSource();
    }

    @Test
    void givenValidFile_whenGetArtists_thenReturnsNonEmptyArtistList() {
        List<Artist> artists = artistsListSource.extractAvailableArtists();

        assertNotNull(artists, "Artists list should not be null.");
        assertFalse(artists.isEmpty(), "Artists list should not be empty.");
    }

    @Test
    void givenValidFile_whenGetArtists_thenCorrectNumberOfAvailableArtistsReturned() {
        List<Artist> artists = artistsListSource.extractAvailableArtists();

        assertEquals(EXPECTED_AVAILABLE_ARTISTS_COUNT, artists.size(),
                     "The number of available artists should match the valid rows in the test Excel file.");
    }

    @Test
    void givenValidFile_whenGetArtists_thenAvailableArtistsDataMatchesExcelFile() {
        List<Artist> artists = artistsListSource.extractAvailableArtists();

        assertTrue(artistsMatchExpected(EXPECTED_ARTISTS, artists),
                   "The artists list should match the expected data from the Excel file.");
    }

    private boolean artistsMatchExpected(List<Artist> expectedArtists, List<Artist> actualArtists) {
        if (expectedArtists.size() != actualArtists.size()) {
            return false;
        }
        for (int i = 0; i < expectedArtists.size(); i++) {
            if (! artistAttributesMatch(expectedArtists.get(i), actualArtists.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean artistAttributesMatch(Artist expected, Artist actual) {
        return expected.getName().equals(actual.getName()) &&
               expected.getCost().equals(actual.getCost()) &&
               expected.getPopularity() == actual.getPopularity() &&
               expected.getGroupPeopleCount() == actual.getGroupPeopleCount() &&
               expected.getMusicalGenre().equals(actual.getMusicalGenre());
    }
}
