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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MinimizeCostStrategyTest {
    private final BigDecimal FIVE_HUNDRED_THOUSAND = BigDecimal.valueOf(500000.00);
    private final BigDecimal THREE_HUNDRED_THOUSAND = BigDecimal.valueOf(300000.00);
    private final int FIRST = 1;
    private final int SECOND = 2;
    private final BigDecimal TWO_HUNDRED_THOUSAND = BigDecimal.valueOf(200000.00);
    private final int HEADLINER_NUMBER = 0;
    @InjectMocks
    MinimizeCostStrategy strategy;
    @Mock
    Artist artist1;
    @Mock
    Artist artist2;
    @Mock
    Artist artist3;
    @Mock
    MoneyAmount budget;

    @BeforeEach
    void setUp() {
        strategy = new MinimizeCostStrategy();
    }

    @Test
    void givenListOfArtist_whenSelectTwoArtist_thenCheapestArtistsAreSelect() {
        when(artist1.getCostValue()).thenReturn(TWO_HUNDRED_THOUSAND);
        when(artist2.getCostValue()).thenReturn(FIVE_HUNDRED_THOUSAND);
        when(artist3.getCostValue()).thenReturn(THREE_HUNDRED_THOUSAND);
        List<Artist> artistList = Arrays.asList(artist1, artist2, artist3);

        List<Artist> selectedArtist = strategy.select(artistList, 2, budget, HEADLINER_NUMBER);

        assertEquals(2, selectedArtist.size());
        assertEquals(artist1, selectedArtist.get(0));
        assertEquals(artist3, selectedArtist.get(1));
    }

    @Test
    void givenArtistsHavingSameCostList_whenSelectTwoArtist_thenTheMostPopularArtistShouldBeSelectedFirst() {
        when(artist1.getCostValue()).thenReturn(TWO_HUNDRED_THOUSAND);
        when(artist2.getCostValue()).thenReturn(TWO_HUNDRED_THOUSAND);
        when(artist1.getPopularity()).thenReturn(SECOND);
        when(artist2.getPopularity()).thenReturn(FIRST);
        List<Artist> artistList = Arrays.asList(artist1, artist2);

        List<Artist> selectedArtist = strategy.select(artistList, 2, budget, HEADLINER_NUMBER);

        assertEquals(2, selectedArtist.size());
        assertEquals(artist2, selectedArtist.get(0));
        assertEquals(artist1, selectedArtist.get(1));
    }
}
