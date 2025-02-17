package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.ConfirmedProgram;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.SchedulingType;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.selection.SelectionCriteria;
import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.ProgramConfirmRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ProgramConfirmResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ScheduleResponseDTO;
import ca.ulaval.glo4002.application.services.requests.ProgramConfirmRequest;
import ca.ulaval.glo4002.application.utils.DateUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgramMapper {

    public ProgramConfirmRequest toProgramConfirmRequest(ProgramConfirmRequestDTO programConfirmRequestDTO) {
        LocalDate confirmationDate = DateUtils.parseToLocalDate(programConfirmRequestDTO.confirmationDate());
        SelectionCriteria selectionCriteria = SelectionCriteria.fromString(programConfirmRequestDTO.criteria());
        SchedulingType schedulingType = SchedulingType.valueOf(programConfirmRequestDTO.scheduling().toUpperCase());
        MoneyAmount headlinerBudget = new MoneyAmount(programConfirmRequestDTO.headlinerBudget());

        return new ProgramConfirmRequest(confirmationDate, selectionCriteria, schedulingType, headlinerBudget,
                                         programConfirmRequestDTO.headlinerLimit());
    }

    public ProgramConfirmResponseDTO toProgramConfirmResponseDTO(ConfirmedProgram confirmedProgram) {
        List<ScheduleResponseDTO> scheduleDTOs =
                confirmedProgram.getSchedule().entrySet().stream().sorted(Map.Entry.comparingByKey())
                        .map(entry -> new ScheduleResponseDTO(DateUtils.formatLocalDate(entry.getKey()),
                                                              entry.getValue().getName())).collect(Collectors.toList());

        return new ProgramConfirmResponseDTO(scheduleDTOs, confirmedProgram.getTotalCostForArtists().twoDecimals(),
                                             confirmedProgram.getTotalCost().twoDecimals());
    }

}