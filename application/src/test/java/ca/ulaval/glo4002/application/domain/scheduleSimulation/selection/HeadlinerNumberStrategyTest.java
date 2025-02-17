package ca.ulaval.glo4002.application.domain.scheduleSimulation.selection;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HeadlinerNumberStrategyTest {
    private final BigDecimal HIGH_PRICE = BigDecimal.valueOf(200000.00);
    private final BigDecimal LOW_PRICE = BigDecimal.valueOf(1000.00);
    private final int FIRST = 1;
    private final int SECOND = 2;
    private final int THIRD = 3;
    private final int FOURTH = 4;
    @InjectMocks
    HeadlinerNumberStrategy strategy;
    @Mock
    Artist firstPopularArtist;
    @Mock
    Artist secondPopularArtist;
    @Mock
    Artist thirdArtist;
    @Mock
    Artist fourthArtist;
    @Mock
    MoneyAmount budget;

    @BeforeEach
    void setUp() {
        strategy = new HeadlinerNumberStrategy();
        when(firstPopularArtist.getPopularity()).thenReturn(FIRST);
        when(secondPopularArtist.getPopularity()).thenReturn(SECOND);
    }

    @Test
    void givenArtistListAndHeadlinerNumberEqualsTwo_whenSelectTwoArtist_thenTwoPopularArtistsAreSelected() {
        int HEADLINER_NUMBER = 2;
        when(thirdArtist.getPopularity()).thenReturn(FOURTH);
        when(fourthArtist.getPopularity()).thenReturn(THIRD);
        List<Artist> artistList = Arrays.asList(firstPopularArtist, secondPopularArtist, thirdArtist, fourthArtist);

        List<Artist> selectedArtist = strategy.select(artistList, 2, budget, HEADLINER_NUMBER);

        assertEquals(2, selectedArtist.size());
        assertEquals(secondPopularArtist, selectedArtist.get(1));
        assertEquals(firstPopularArtist, selectedArtist.get(0));

    }

    @Test
    void givenArtistListAndHeadlinerNumberEqualsTwo_whenSelectThreeArtist_thenFillRemainingWithCheapest() {
        int HEADLINER_NUMBER = 2;
        when(thirdArtist.getCostValue()).thenReturn(LOW_PRICE);
        when(thirdArtist.getPopularity()).thenReturn(FOURTH);
        when(fourthArtist.getCostValue()).thenReturn(HIGH_PRICE);
        when(fourthArtist.getPopularity()).thenReturn(THIRD);
        List<Artist> artistList = Arrays.asList(firstPopularArtist, secondPopularArtist, thirdArtist, fourthArtist);

        List<Artist> selectedArtist = strategy.select(artistList, 3, budget, HEADLINER_NUMBER);

        assertEquals(3, selectedArtist.size());
        assertTrue(selectedArtist.contains(thirdArtist));
        assertFalse(selectedArtist.contains(fourthArtist));

    }

    @Test
    void givenArtistHavingSameCostList_whenSortArtistsByCost_thenTheMostPopularArtistShouldBeSelectedFirst() {
        when(firstPopularArtist.getCostValue()).thenReturn(LOW_PRICE);
        when(secondPopularArtist.getCostValue()).thenReturn(LOW_PRICE);
        List<Artist> artistList = Arrays.asList(firstPopularArtist, secondPopularArtist);

        List<Artist> selectedArtist = strategy.sortArtistsByCost(artistList, 2);

        assertEquals(firstPopularArtist, selectedArtist.get(0));
        assertEquals(secondPopularArtist, selectedArtist.get(1));

    }
}
