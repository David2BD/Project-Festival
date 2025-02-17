package ca.ulaval.glo4002.application.interfaces.rest.exceptionsMappers;

import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ErrorResponse;
import ca.ulaval.glo4002.application.interfaces.rest.exceptions.InvalidFormatException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InterfacesInvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException> {
    @Override
    public Response toResponse(InvalidFormatException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("INVALID_FORMAT", exception.getMessage())).build();
    }
}





