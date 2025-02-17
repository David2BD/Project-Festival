package ca.ulaval.glo4002.application.interfaces.rest;


import ca.ulaval.glo4002.application.domain.reports.OxygenReport;
import ca.ulaval.glo4002.application.domain.reports.ProfitReport;
import ca.ulaval.glo4002.application.interfaces.rest.dto.mappers.OxygenReportMapper;
import ca.ulaval.glo4002.application.interfaces.rest.dto.mappers.ProfitReportMapper;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.OxygenReportResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ProfitReportResponseDTO;
import ca.ulaval.glo4002.application.services.ReportService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/reports")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReportResource {
    private final ReportService reportService;

    @Inject
    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    @GET
    @Path("/o2")
    public Response getOxygenReport() {
        OxygenReport oxygenReport = reportService.getOxygenReport();
        OxygenReportResponseDTO oxygenReportResponseDTO = OxygenReportMapper.toDTO(oxygenReport);
        return Response.status(Response.Status.OK).entity(oxygenReportResponseDTO).build();
    }

    @GET
    @Path("/profits")
    public Response getProfitsReport() {
        ProfitReport profitReport = reportService.getProfitReport();
        ProfitReportResponseDTO profitReportResponseDTO = ProfitReportMapper.toProfitReportResponseDTO(profitReport);
        return Response.status(Response.Status.OK).entity(profitReportResponseDTO).build();
    }

}
