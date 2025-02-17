package ca.ulaval.glo4002.application.domain.scheduleSimulation;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistTest {
    private final String NAME = "Sun 41";
    private final int POPULARITY = 1;
    private final int GROUP_PEOPLE_COUNT = 4;
    private final String MUSICAL_GENRE = "rock";
    @Mock
    MoneyAmount cost;
    @Mock
    MoneyAmount budget;
    Artist artist;

    @BeforeEach
    void setUp() {
        artist = new Artist(NAME, cost, POPULARITY, GROUP_PEOPLE_COUNT, MUSICAL_GENRE);
    }

    @Test()
    void givenBudgetGreaterOrEqualToArtistCost_WhenSubtractFromBudgetIfPossible_thenBudgetIsReduced() {
        when(budget.isGreaterOrEqualTo(cost)).thenReturn(Boolean.TRUE);

        artist.subtractFromBudgetIfPossible(budget);

        verify(budget).subtract(cost);
    }

    @Test()
    void givenBudgetLessThanArtistCost_WhenSubtractFromBudgetIfPossible_thenBudgetIsUnchanged() {
        when(budget.isGreaterOrEqualTo(cost)).thenReturn(Boolean.FALSE);

        MoneyAmount amount = artist.subtractFromBudgetIfPossible(budget);

        assertEquals(amount, budget);
    }

}
