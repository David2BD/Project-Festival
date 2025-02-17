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
class RollercoasterStrategyTest {

    private RollercoasterStrategy strategy;
    private final LocalDate ANY_DATE = LocalDate.of(2060, 7, 10);
    private List<Artist> listOfFourArtists;

    @Mock
    Artist popularity1ArtistMock;

    @Mock
    Artist popularity2ArtistMock;

    @Mock
    Artist popularity3ArtistMock;

    @Mock
    Artist popularity4ArtistMock;

    @BeforeEach
    void setUp() {
        strategy = new RollercoasterStrategy();
        when(popularity1ArtistMock.getPopularity()).thenReturn(1);
        when(popularity2ArtistMock.getPopularity()).thenReturn(2);
        when(popularity3ArtistMock.getPopularity()).thenReturn(3);
        when(popularity4ArtistMock.getPopularity()).thenReturn(4);
        listOfFourArtists = new ArrayList<>(
                Arrays.asList(popularity1ArtistMock, popularity2ArtistMock, popularity3ArtistMock,
                              popularity4ArtistMock));
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
    void givenListOf4Artist_whenScheduling_thenScheduleHasFourEntries() {
        Map<LocalDate, Artist> returnedSchedule = strategy.schedule(listOfFourArtists, ANY_DATE);

        assertEquals(4, returnedSchedule.size());
    }

    @Test
    void givenListOfArtist_whenScheduling_thenArtistOnFirstDayIsMostPopular() {
        Map<LocalDate, Artist> returnedSchedule = strategy.schedule(listOfFourArtists, ANY_DATE);

        assertEquals(popularity1ArtistMock, returnedSchedule.get(ANY_DATE));
    }

    @Test
    void givenListOfArtist_whenScheduling_thenArtistOnLastDayIsSecondMostPopular() {
        Map<LocalDate, Artist> returnedSchedule = strategy.schedule(listOfFourArtists, ANY_DATE);

        assertEquals(popularity2ArtistMock, returnedSchedule.get(ANY_DATE.plusDays(3)));
    }

    @Test
    void givenListOfArtist_whenScheduling_thenArtistBetWeenFirstAndLastAreInDecreasingPopularity() {
        Map<LocalDate, Artist> returnedSchedule = strategy.schedule(listOfFourArtists, ANY_DATE);

        assertEquals(popularity3ArtistMock, returnedSchedule.get(ANY_DATE.plusDays(1)));
        assertEquals(popularity4ArtistMock, returnedSchedule.get(ANY_DATE.plusDays(2)));
    }
}
