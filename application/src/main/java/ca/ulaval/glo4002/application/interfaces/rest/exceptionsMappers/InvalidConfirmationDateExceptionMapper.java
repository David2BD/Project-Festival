package ca.ulaval.glo4002.application.interfaces.rest.exceptionsMappers;

import ca.ulaval.glo4002.application.domain.festival.InvalidConfirmationDateException;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidConfirmationDateExceptionMapper implements ExceptionMapper<InvalidConfirmationDateException> {
    @Override
    public Response toResponse(InvalidConfirmationDateException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("INVALID_CONFIRMATION_DATE", exception.getMessage())).build();
    }

}
