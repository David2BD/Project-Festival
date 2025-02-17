package ca.ulaval.glo4002.application.interfaces.rest;

import ca.ulaval.glo4002.application.domain.transport.manifest.ShuttleManifest;
import ca.ulaval.glo4002.application.interfaces.rest.dto.mappers.ShuttleManifestMapper;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ShuttleManifestResponseDTO;
import ca.ulaval.glo4002.application.services.ShuttleManifestService;
import ca.ulaval.glo4002.application.utils.DateUtils;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

@Path("/shuttle-manifests")
@Produces(MediaType.APPLICATION_JSON)
public class ShuttleManifestResource {

    private final ShuttleManifestService shuttleManifestService;
    private final ShuttleManifestMapper shuttleManifestMapper;

    @Inject
    public ShuttleManifestResource(
            ShuttleManifestMapper shuttleManifestMapper, ShuttleManifestService shuttleManifestService
    ) {
        this.shuttleManifestMapper = shuttleManifestMapper;
        this.shuttleManifestService = shuttleManifestService;
    }

    @GET
    public Response getShuttleManifest(@QueryParam("date") String dateStr) throws IllegalArgumentException {
        LocalDate date = DateUtils.parseToLocalDate(dateStr);
        ShuttleManifest shuttleManifest = shuttleManifestService.generateShuttleManifest(date);
        ShuttleManifestResponseDTO shuttleManifestResponseDTO =
                this.shuttleManifestMapper.toShuttleManifestResponseDTO(shuttleManifest);
        return Response.status(Response.Status.OK).entity(shuttleManifestResponseDTO).build();
    }
}
