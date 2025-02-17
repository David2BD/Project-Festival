package ca.ulaval.glo4002.application.interfaces.rest.exceptionsMappers;

import ca.ulaval.glo4002.application.domain.festival.OrderNotFoundException;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class OrderNotFoundExceptionMapper implements ExceptionMapper<OrderNotFoundException> {
    @Override
    public Response toResponse(OrderNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse("ORDER_NOT_FOUND", exception.getMessage())).build();
    }
}
