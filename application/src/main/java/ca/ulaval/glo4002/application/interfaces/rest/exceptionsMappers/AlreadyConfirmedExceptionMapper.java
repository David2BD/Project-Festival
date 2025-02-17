package ca.ulaval.glo4002.application.interfaces.rest.exceptionsMappers;

import ca.ulaval.glo4002.application.domain.festival.AlreadyConfirmedException;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AlreadyConfirmedExceptionMapper implements ExceptionMapper<AlreadyConfirmedException> {
    @Override
    public Response toResponse(AlreadyConfirmedException exception) {
        return Response.status(Response.Status.CONFLICT).entity(new ErrorResponse("ALREADY_CONFIRMED", exception.getMessage())).build();
    }

}
