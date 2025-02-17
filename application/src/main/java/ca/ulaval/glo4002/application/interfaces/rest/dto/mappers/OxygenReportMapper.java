package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.application.domain.reports.OxygenLogEntry;
import ca.ulaval.glo4002.application.domain.reports.OxygenReport;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.OxygenLogEntryResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.OxygenReportResponseDTO;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OxygenReportMapper {

    public static OxygenReportResponseDTO toDTO(OxygenReport oxygenReport) {
        List<Map<String, Object>> formattedInventory = formatInventory(oxygenReport.inventory());
        List<OxygenLogEntryResponseDTO> formattedLogs = formatLogEntries(oxygenReport.oxygenLogEntries());
        BigDecimal totalCost = oxygenReport.calculateOxygenCost().twoDecimals();

        return new OxygenReportResponseDTO(formattedInventory, totalCost, formattedLogs);
    }

    private static OxygenLogEntryResponseDTO toLogEntryDTO(OxygenLogEntry entry) {
        return new OxygenLogEntryResponseDTO(entry.getSupplyType().getDisplayName(), entry.getOxygenGrade().name(),
                                             entry.getQuantity(), entry.getTimestamp().toString());
    }

    private static List<Map<String, Object>> formatInventory(Map<OxygenGrade, Integer> inventory) {
        return Stream.of(OxygenGrade.values()).map(grade -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("gradeTankOxygen", grade.toString());
            map.put("quantity", inventory.get(grade));
            return map;
        }).collect(Collectors.toList());
    }

    private static List<OxygenLogEntryResponseDTO> formatLogEntries(TreeSet<OxygenLogEntry> logEntries) {
        return logEntries.stream().map(OxygenReportMapper::toLogEntryDTO).collect(Collectors.toList());
    }
}
