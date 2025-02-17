package ca.ulaval.glo4002.application.domain.reports;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.application.domain.oxygen.SupplyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class OxygenLogEntryTest {
    private static final int A_QUANTITY = 6;
    private static final MoneyAmount COST = new MoneyAmount(10);
    static final LocalDateTime ANY_TIMESTAMP = LocalDateTime.now();

    private OxygenLogEntry entry;

    @Mock
    OxygenGrade oxygenGradeMock;

    @BeforeEach
    void setUp() {
        lenient().when(oxygenGradeMock.calculateCost(A_QUANTITY)).thenReturn(COST);
    }

    @Test
    void givenLogEntryOfTypeBought_whenCalculateEntryCost_thenReturnCost() {
        entry = new OxygenLogEntry(SupplyType.BOUGHT, oxygenGradeMock, A_QUANTITY, ANY_TIMESTAMP);

        MoneyAmount result = entry.calculateEntryCost();

        assertEquals(COST, result);
    }

    @Test
    void givenLogEntryOfTypeProduced_whenCalculateEntryCost_thenReturnCost() {
        entry = new OxygenLogEntry(SupplyType.PRODUCED, oxygenGradeMock, A_QUANTITY, ANY_TIMESTAMP);

        MoneyAmount result = entry.calculateEntryCost();

        assertEquals(COST, result);
    }

    @Test
    void givenLogEntryOfTypeInventoryUsed_whenCalculateEntryCost_thenReturnZero() {
        entry = new OxygenLogEntry(SupplyType.INVENTORY_USED, oxygenGradeMock, A_QUANTITY, ANY_TIMESTAMP);

        MoneyAmount result = entry.calculateEntryCost();
        MoneyAmount expected = new MoneyAmount(0);

        assertEquals(expected, result);
    }
}