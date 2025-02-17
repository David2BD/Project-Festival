package ca.ulaval.glo4002.application.interfaces.rest.exceptionsMappers;

import ca.ulaval.glo4002.application.domain.pass.exceptions.PassCreationException;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PassCreationExceptionMapper implements ExceptionMapper<PassCreationException> {
    @Override
    public Response toResponse(PassCreationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("INVALID_FORMAT", exception.getMessage())).build();
    }
}
