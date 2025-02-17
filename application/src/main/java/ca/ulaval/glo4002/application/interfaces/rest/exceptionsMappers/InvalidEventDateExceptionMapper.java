package ca.ulaval.glo4002.application.interfaces.rest.exceptionsMappers;

import ca.ulaval.glo4002.application.domain.pass.exceptions.InvalidEventDateException;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidEventDateExceptionMapper implements ExceptionMapper<InvalidEventDateException> {
    @Override
    public Response toResponse(InvalidEventDateException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("INVALID_EVENT_DATE", exception.getMessage())).build();
    }

}
