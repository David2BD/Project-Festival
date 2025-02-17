package ca.ulaval.glo4002.application.interfaces.rest.dto.responses;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OxygenReportResponseDTO {

    public List<Map<String, Object>> inventory;
    public BigDecimal totalCost;
    public List<OxygenLogEntryResponseDTO> logs;

    public OxygenReportResponseDTO(List<Map<String, Object>> inventory, BigDecimal totalCost, List<OxygenLogEntryResponseDTO> logs) {
        this.inventory = inventory;
        this.totalCost = totalCost;
        this.logs = logs;
    }


}
