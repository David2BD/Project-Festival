package ca.ulaval.glo4002.application.interfaces.rest;

import ca.ulaval.glo4002.application.domain.scheduleSimulation.ConfirmedProgram;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.ScheduleSimulation;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.SchedulingType;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.selection.SelectionCriteria;
import ca.ulaval.glo4002.application.interfaces.rest.dto.mappers.ProgramMapper;
import ca.ulaval.glo4002.application.interfaces.rest.dto.mappers.SimulationReportMapper;
import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.ProgramConfirmRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ProgramConfirmResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.SimulationReportResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.validators.ProgramRequestValidator;
import ca.ulaval.glo4002.application.services.ProgramService;
import ca.ulaval.glo4002.application.services.requests.ProgramConfirmRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;

@Path("/program")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProgramResource {
    private final ProgramService programService;
    private final ProgramRequestValidator programRequestValidator;
    private final ProgramMapper programMapper;
    private final SimulationReportMapper simulationReportMapper;

    @Inject
    public ProgramResource(
            ProgramService programService, ProgramRequestValidator programRequestValidator, ProgramMapper programMapper,
            SimulationReportMapper simulationReportMapper
    ) {
        this.programService = programService;
        this.programRequestValidator = programRequestValidator;
        this.programMapper = programMapper;
        this.simulationReportMapper = simulationReportMapper;
    }

    @GET
    @Path("/simulation")
    public SimulationReportResponseDTO simulateSchedule(
            @QueryParam("criteria") SelectionCriteria selectionCriteria,
            @QueryParam("scheduling") SchedulingType schedulingType,
            @QueryParam("headlinerBudget") String headlinerBudget, @QueryParam("headlinerLimit") Integer headlinerLimit
    ) {
        headlinerBudget = (headlinerBudget == null) ? "0" : headlinerBudget;
        headlinerLimit = (headlinerLimit == null) ? 0 : headlinerLimit;

        ScheduleSimulation scheduleSimulation = programService.getSimulation(selectionCriteria, schedulingType, headlinerBudget, headlinerLimit);

        return simulationReportMapper.toDto(scheduleSimulation);
    }

    @POST
    public Response createProgram(ProgramConfirmRequestDTO programConfirmRequestDTO) {
        programRequestValidator.validate(programConfirmRequestDTO);
        ProgramConfirmRequest programConfirmRequest = programMapper.toProgramConfirmRequest(programConfirmRequestDTO);
        programService.confirmProgram(programConfirmRequest);

        URI location = UriBuilder.fromPath("/program/confirmed").build();
        return Response.created(location).build();
    }

    @GET
    @Path("/confirmed")
    public Response getConfirmedProgram() {
        ConfirmedProgram confirmedProgram = programService.getConfirmedProgram();
        ProgramConfirmResponseDTO confirmedProgramDTO = programMapper.toProgramConfirmResponseDTO(confirmedProgram);
        return Response.ok(confirmedProgramDTO).build();
    }
}

