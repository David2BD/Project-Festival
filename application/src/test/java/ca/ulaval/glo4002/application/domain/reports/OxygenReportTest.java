package ca.ulaval.glo4002.application.domain.reports;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OxygenReportTest {
    private static final Map<OxygenGrade, Integer> inventory = new HashMap<>();
    private static final LocalDateTime ANY_TIMESTAMP = LocalDateTime.now();
    private static final UUID ANY_ID = UUID.randomUUID();
    private static final int AN_AMOUNT = 10;
    private static final int ANOTHER_AMOUNT = 15;
    private static final MoneyAmount AN_ENTRY_COST = new MoneyAmount(AN_AMOUNT);
    private static final MoneyAmount ANOTHER_ENTRY_COST = new MoneyAmount(ANOTHER_AMOUNT);
    private final TreeSet<OxygenLogEntry> oxygenLogEntries =
            new TreeSet<>(Comparator.comparing(OxygenLogEntry::getTimestamp).thenComparing(OxygenLogEntry::getId));

    @Mock
    OxygenLogEntry oxygenLogEntryMock;

    @Mock
    OxygenLogEntry otherOxygenLogEntryMock;

    OxygenReport oxygenReport;

    @BeforeEach
    void setUp() {
        lenient().when(oxygenLogEntryMock.getTimestamp()).thenReturn(ANY_TIMESTAMP);
        lenient().when(oxygenLogEntryMock.getId()).thenReturn(ANY_ID);
        when(oxygenLogEntryMock.calculateEntryCost()).thenReturn(AN_ENTRY_COST);

        when(otherOxygenLogEntryMock.getTimestamp()).thenReturn(ANY_TIMESTAMP.minusDays(3));
        when(otherOxygenLogEntryMock.getId()).thenReturn(ANY_ID);
        when(otherOxygenLogEntryMock.calculateEntryCost()).thenReturn(ANOTHER_ENTRY_COST);

        oxygenLogEntries.add(otherOxygenLogEntryMock);
        oxygenLogEntries.add(oxygenLogEntryMock);

        oxygenReport = new OxygenReport(inventory, oxygenLogEntries);
    }

    @Test
    void givenLogEntries_whenCalculateOxygenCost_thenCalculateEntryCostCalled() {
        oxygenReport.calculateOxygenCost();

        verify(oxygenLogEntryMock).calculateEntryCost();
    }

    @Test
    void givenLogEntries_whenCalculateOxygenCost_thenTotalCostIsSumOfEntriesCost() {
        MoneyAmount totalCost = oxygenReport.calculateOxygenCost();
        MoneyAmount expectedCost = new MoneyAmount(AN_AMOUNT + ANOTHER_AMOUNT);

        assertEquals(expectedCost.getAmount(), totalCost.getAmount());
    }
}