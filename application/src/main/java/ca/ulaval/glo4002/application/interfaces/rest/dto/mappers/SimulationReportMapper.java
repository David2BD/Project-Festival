package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.scheduleSimulation.ScheduleSimulation;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ScheduleEntryResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.SimulationReportResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationReportMapper {

    public SimulationReportResponseDTO toDto(ScheduleSimulation report) {
        List<ScheduleEntryResponseDTO> schedule = report.getSchedule().entrySet().stream()
                .map(entry -> new ScheduleEntryResponseDTO(entry.getKey().toString(), entry.getValue().getName()))
                .collect(Collectors.toList());

        BigDecimal totalArtistCost = report.getTotalCostForArtists().twoDecimals();

        return new SimulationReportResponseDTO(schedule, totalArtistCost);
    }

}
