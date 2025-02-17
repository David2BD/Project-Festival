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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeadlinerBudgetStrategyTest {
    private final BigDecimal OVER_BUDGET_PRICE = BigDecimal.valueOf(5000000.00);
    private final BigDecimal TWO_THOUSAND = BigDecimal.valueOf(2000.00);
    private final BigDecimal FIVE_HUNDRED = BigDecimal.valueOf(500.00);
    private final int FIRST = 1;
    private final int SECOND = 2;
    private final int THIRD = 3;
    private final int NUMBER_OF_ARTIST_TO_SELECT = 2;
    private final int HEADLINER_NUMBER = 0;

    @InjectMocks
    HeadlinerBudgetStrategy strategy;
    @Mock
    Artist firstPopularArtist;
    @Mock
    Artist secondPopularArtist;
    @Mock
    Artist thirdPopularArtist;
    @Mock
    MoneyAmount budget;
    @Mock
    MoneyAmount remainingBudgetAfterAddFirstArtist;
    @Mock
    MoneyAmount remainingBudgetAfterAddSecondArtist;

    @BeforeEach
    void setUp() {
        strategy = new HeadlinerBudgetStrategy();
        lenient().when(firstPopularArtist.getPopularity()).thenReturn(FIRST);
        lenient().when(thirdPopularArtist.getPopularity()).thenReturn(THIRD);
        lenient().when(secondPopularArtist.getPopularity()).thenReturn(SECOND);
    }

    @Test
    void givenArtistsWithinBudgetList_whenSelect_thenOnlyTheMostPopularArtistAreSelected() {
        List<Artist> artistList = Arrays.asList(firstPopularArtist, thirdPopularArtist, secondPopularArtist);
        when(firstPopularArtist.subtractFromBudgetIfPossible(budget)).thenReturn(remainingBudgetAfterAddFirstArtist);
        when(secondPopularArtist.subtractFromBudgetIfPossible(remainingBudgetAfterAddFirstArtist)).thenReturn(
                remainingBudgetAfterAddSecondArtist);

        List<Artist> selectedArtists =
                strategy.select(artistList, NUMBER_OF_ARTIST_TO_SELECT, budget, HEADLINER_NUMBER);

        verify(firstPopularArtist).subtractFromBudgetIfPossible(budget);
        verify(secondPopularArtist).subtractFromBudgetIfPossible(remainingBudgetAfterAddFirstArtist);
        assertEquals(NUMBER_OF_ARTIST_TO_SELECT, selectedArtists.size());
        assertTrue(selectedArtists.contains(firstPopularArtist));
        assertTrue(selectedArtists.contains(secondPopularArtist));
        assertTrue(selectedArtists.indexOf(firstPopularArtist) < selectedArtists.indexOf(secondPopularArtist));
    }

    @Test
    void givenArtistListContainArtistExceedingBudget_whenSelect_thenPopularArtistsWithinBudgetAreSelected() {
        List<Artist> artistList = Arrays.asList(firstPopularArtist, secondPopularArtist, thirdPopularArtist);
        lenient().when(firstPopularArtist.getCostValue()).thenReturn(OVER_BUDGET_PRICE);
        when(firstPopularArtist.subtractFromBudgetIfPossible(budget)).thenReturn(budget);
        when(secondPopularArtist.subtractFromBudgetIfPossible(budget)).thenReturn(remainingBudgetAfterAddFirstArtist);
        when(thirdPopularArtist.subtractFromBudgetIfPossible(remainingBudgetAfterAddFirstArtist)).thenReturn(
                remainingBudgetAfterAddSecondArtist);

        List<Artist> selectedArtists =
                strategy.select(artistList, NUMBER_OF_ARTIST_TO_SELECT, budget, HEADLINER_NUMBER);

        verify(firstPopularArtist).subtractFromBudgetIfPossible(budget);
        verify(secondPopularArtist).subtractFromBudgetIfPossible(budget);
        verify(thirdPopularArtist).subtractFromBudgetIfPossible(remainingBudgetAfterAddFirstArtist);
        assertEquals(NUMBER_OF_ARTIST_TO_SELECT, selectedArtists.size());
        assertTrue(selectedArtists.contains(secondPopularArtist));
        assertTrue(selectedArtists.contains(thirdPopularArtist));
        assertFalse(selectedArtists.contains(firstPopularArtist));
    }

    @Test
    void givenListOfArtists_whenGetArtistsSortedByPopular_thenArtistsShouldBeSortedByPopularity() {
        List<Artist> artistList = Arrays.asList(secondPopularArtist, thirdPopularArtist, firstPopularArtist);
        lenient().when(firstPopularArtist.getPopularity()).thenReturn(FIRST);
        when(secondPopularArtist.getPopularity()).thenReturn(SECOND);
        when(thirdPopularArtist.getPopularity()).thenReturn(THIRD);

        List<Artist> popularArtists = strategy.getArtistsSortedByPopular(artistList);

        assertEquals(3, popularArtists.size());
        assertEquals(firstPopularArtist, popularArtists.get(0));
        assertEquals(secondPopularArtist, popularArtists.get(1));
        assertEquals(thirdPopularArtist, popularArtists.get(2));

    }

    @Test
    void givenArtistListContainSelectedArtist_whenGetUnselectedArtistsSortedByCost_thenUnselectedArtistShouldBeSortedByCost() {
        when(firstPopularArtist.getCostValue()).thenReturn(TWO_THOUSAND);
        when(thirdPopularArtist.getCostValue()).thenReturn(FIVE_HUNDRED);
        List<Artist> artistList = Arrays.asList(firstPopularArtist, secondPopularArtist, thirdPopularArtist);
        List<Artist> selectedArtistList = List.of(secondPopularArtist);

        List<Artist> unselectedArtistsSortedByCost =
                strategy.getUnselectedArtistsSortedByCost(artistList, selectedArtistList);

        assertEquals(2, unselectedArtistsSortedByCost.size());
        assertEquals(thirdPopularArtist, unselectedArtistsSortedByCost.get(0));
        assertEquals(firstPopularArtist, unselectedArtistsSortedByCost.get(1));
        assertFalse(unselectedArtistsSortedByCost.contains(secondPopularArtist));

    }

    @Test
    void givenArtistsHavingSameCostList_whenSortArtistListByCost_thenTheMostPopularArtistShouldBeSelectedFirst() {
        when(firstPopularArtist.getCostValue()).thenReturn(TWO_THOUSAND);
        when(secondPopularArtist.getCostValue()).thenReturn(TWO_THOUSAND);
        List<Artist> artistList = Arrays.asList(secondPopularArtist, firstPopularArtist);

        List<Artist> artistsListSortedByCost = strategy.sortArtistListByCost(artistList);

        assertEquals(firstPopularArtist, artistsListSortedByCost.get(0));
        assertEquals(secondPopularArtist, artistsListSortedByCost.get(1));

    }

}

