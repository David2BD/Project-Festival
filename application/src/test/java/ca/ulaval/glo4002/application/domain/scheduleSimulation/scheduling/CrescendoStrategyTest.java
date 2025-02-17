package ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling;

import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrescendoStrategyTest {

    private CrescendoStrategy strategy;
    private final LocalDate ANY_DATE = LocalDate.of(2060, 7, 10);
    private List<Artist> listOfFourArtists;

    @Mock
    Artist POPULARITY_1_ARTIST;

    @Mock
    Artist POPULARITY_2_ARTIST;

    @Mock
    Artist POPULARITY_3_ARTIST;

    @Mock
    Artist POPULARITY_4_ARTIST;

    @BeforeEach
    void setUp() {
        strategy = new CrescendoStrategy();
        when(POPULARITY_1_ARTIST.getPopularity()).thenReturn(1);
        when(POPULARITY_2_ARTIST.getPopularity()).thenReturn(2);
        when(POPULARITY_3_ARTIST.getPopularity()).thenReturn(3);
        when(POPULARITY_4_ARTIST.getPopularity()).thenReturn(4);
        listOfFourArtists = new ArrayList<>(
                Arrays.asList(POPULARITY_4_ARTIST, POPULARITY_2_ARTIST, POPULARITY_1_ARTIST, POPULARITY_3_ARTIST));
    }

    @Test
    void givenListOfArtist_whenScheduling_thenScheduleHasFourConsecutivesDates() {
        Map<LocalDate, Artist> returnedSchedule = strategy.schedule(listOfFourArtists, ANY_DATE);

        assertTrue(returnedSchedule.containsKey(ANY_DATE));
        assertTrue(returnedSchedule.containsKey(ANY_DATE.plusDays(1)));
        assertTrue(returnedSchedule.containsKey(ANY_DATE.plusDays(2)));
        assertTrue(returnedSchedule.containsKey(ANY_DATE.plusDays(3)));
    }

    @Test
    void givenListOfArtist_whenScheduling_thenArtistsAreInIncreasingPopularityOrderFromInitialDate() {
        Map<LocalDate, Artist> returnedSchedule = strategy.schedule(listOfFourArtists, ANY_DATE);

        assertEquals(POPULARITY_4_ARTIST, returnedSchedule.get(ANY_DATE));
        assertEquals(POPULARITY_3_ARTIST, returnedSchedule.get(ANY_DATE.plusDays(1)));
        assertEquals(POPULARITY_2_ARTIST, returnedSchedule.get(ANY_DATE.plusDays(2)));
        assertEquals(POPULARITY_1_ARTIST, returnedSchedule.get(ANY_DATE.plusDays(3)));
    }

    @Test
    void givenListOf4Artist_whenScheduling_thenScheduleHasFourEntries() {
        Map<LocalDate, Artist> returnedSchedule = strategy.schedule(listOfFourArtists, ANY_DATE);

        assertEquals(4, returnedSchedule.size());
    }

}
