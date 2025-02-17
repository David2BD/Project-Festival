package ca.ulaval.glo4002.application.interfaces.rest.exceptionsMappers;

import ca.ulaval.glo4002.application.domain.order.InvalidOrderDateException;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidOrderDateExceptionMapper implements ExceptionMapper<InvalidOrderDateException> {

    @Override
    public Response toResponse(InvalidOrderDateException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("INVALID_ORDER_DATE", exception.getMessage())).build();
    }
}
