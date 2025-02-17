package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.application.domain.oxygen.SupplyType;
import ca.ulaval.glo4002.application.domain.reports.OxygenLogEntry;
import ca.ulaval.glo4002.application.domain.reports.OxygenReport;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.OxygenLogEntryResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.OxygenReportResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OxygenReportMapperTest {

    private static final float SOME_TOTAL_COST = 123.456f;
    private static final BigDecimal FORMATTED_TOTAL_COST =
            BigDecimal.valueOf(SOME_TOTAL_COST).setScale(2, RoundingMode.HALF_UP);
    private static final LocalDateTime SOME_TIMESTAMP = LocalDateTime.of(2060, 7, 17, 0, 0);
    private static final String A_GRADE = "A";
    private static final int A_QUANTITY = 5;
    private static final SupplyType A_SUPPLY_TYPE = SupplyType.BOUGHT;
    private static final String FORMATTED_KEY_GRADE = "gradeTankOxygen";
    private static final String FORMATTED_KEY_QUANTITY = "quantity";

    private Map<OxygenGrade, Integer> anInventory;
    private TreeSet<OxygenLogEntry> logEntries;

    @Mock
    private OxygenReport oxygenReportMock;

    @Mock
    private OxygenLogEntry logEntryMock;

    @Mock
    MoneyAmount oxygenCost;

    @BeforeEach
    void setUpLogEntries() {
        anInventory = initializeInventory();
        logEntries = new TreeSet<>(Comparator.comparing(OxygenLogEntry::getTimestamp));
        when(logEntryMock.getTimestamp()).thenReturn(SOME_TIMESTAMP);
        when(logEntryMock.getSupplyType()).thenReturn(A_SUPPLY_TYPE);
        when(logEntryMock.getOxygenGrade()).thenReturn(OxygenGrade.A);
        when(logEntryMock.getQuantity()).thenReturn(A_QUANTITY);
        logEntries.add(logEntryMock);
    }

    @BeforeEach
    void setUpOxygenReportMock() {
        when(oxygenReportMock.inventory()).thenReturn(anInventory);
        when(oxygenReportMock.oxygenLogEntries()).thenReturn(logEntries);
        when(oxygenReportMock.calculateOxygenCost()).thenReturn(oxygenCost);
        when(oxygenReportMock.calculateOxygenCost().twoDecimals()).thenReturn(FORMATTED_TOTAL_COST);
    }

    @Test
    void givenAnyReportInventory_whenToDTO_thenReturnReportResponseDTOWithInventoryOfSameSize() {
        OxygenReportResponseDTO result = OxygenReportMapper.toDTO(oxygenReportMock);

        assertEquals(anInventory.size(), result.inventory.size());
    }

    @Test
    void givenAnyReportInventory_whenToDTO_thenReturnReportResponseDTOWithFormattedInventory() {
        OxygenReportResponseDTO result = OxygenReportMapper.toDTO(oxygenReportMock);

        assertTrue(result.inventory.get(0).containsKey(FORMATTED_KEY_GRADE));
        assertTrue(result.inventory.get(0).containsKey(FORMATTED_KEY_QUANTITY));
    }

    @Test
    void givenReportLogEntries_whenToDTO_thenReturnReportResponseDTOWithLogEntriesOfSameSize() {
        OxygenReportResponseDTO result = OxygenReportMapper.toDTO(oxygenReportMock);

        assertEquals(oxygenReportMock.oxygenLogEntries().size(), result.logs.size());
    }

    @Test
    void givenAReportLogEntry_whenToDTO_thenReturnReportResponseDTOWithFormattedLogEntries() {
        OxygenReportResponseDTO result = OxygenReportMapper.toDTO(oxygenReportMock);
        OxygenLogEntryResponseDTO resultLogEntry = result.logs.get(0);

        assertEquals(A_SUPPLY_TYPE.getDisplayName(), resultLogEntry.type);
        assertEquals(A_GRADE, resultLogEntry.grade);
        assertEquals(SOME_TIMESTAMP.toString(), resultLogEntry.timestamp);
        assertEquals(A_QUANTITY, resultLogEntry.quantity);
    }

    @Test
    void givenAReportCost_whenToDTO_thenReturnReportResponseDTOWithFormattedCost() {
        OxygenReportResponseDTO result = OxygenReportMapper.toDTO(oxygenReportMock);

        assertEquals(FORMATTED_TOTAL_COST, result.totalCost);
    }

    private Map<OxygenGrade, Integer> initializeInventory() {
        Map<OxygenGrade, Integer> inventory = new HashMap<>();
        for (OxygenGrade type : OxygenGrade.values()) {
            inventory.put(type, A_QUANTITY);
        }

        return inventory;
    }
}

