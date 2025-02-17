package ca.ulaval.glo4002.application.interfaces.rest.dto.responses;

import java.math.BigDecimal;
import java.util.List;

public class SimulationReportResponseDTO {
    private final List<ScheduleEntryResponseDTO> schedule;
    private final BigDecimal totalArtistCost;

    public SimulationReportResponseDTO(List<ScheduleEntryResponseDTO> schedule, BigDecimal totalArtistCost) {
        this.schedule = schedule;
        this.totalArtistCost = totalArtistCost;
    }

    public List<ScheduleEntryResponseDTO> getSchedule() {
        return schedule;
    }

    public BigDecimal getTotalArtistCost() {
        return totalArtistCost;
    }
}


