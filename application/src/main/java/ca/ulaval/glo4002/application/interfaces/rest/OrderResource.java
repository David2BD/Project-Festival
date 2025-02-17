package ca.ulaval.glo4002.application.interfaces.rest;

import ca.ulaval.glo4002.application.domain.order.Order;
import ca.ulaval.glo4002.application.interfaces.rest.dto.mappers.OrderMapper;
import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.OrderRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.OrderResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.validators.OrderRequestValidator;
import ca.ulaval.glo4002.application.services.OrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderRequestValidator orderRequestValidator;

    @Inject
    public OrderResource(
            OrderService orderService, OrderMapper orderMapper, OrderRequestValidator orderRequestValidator
    ) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.orderRequestValidator = orderRequestValidator;
    }

    @POST
    public Response createOrder(OrderRequestDTO orderRequestDTO) {
        orderRequestValidator.validate(orderRequestDTO);
        Order order = orderMapper.toOrder(orderRequestDTO);
        Long orderNumber = orderService.createOrder(order);

        URI location = UriBuilder.fromResource(OrderResource.class).path("{orderNumber}").build(orderNumber);

        return Response.created(location).build();
    }

    @GET
    @Path("/{orderNumber}")
    public Response getOrder(@PathParam("orderNumber") Long orderNumber) {
        Order order = orderService.getOrder(orderNumber);
        OrderResponseDTO orderResponseDTO = orderMapper.toOrderResponseDTO(order);
        return Response.status(Response.Status.OK).entity(orderResponseDTO).build();
    }

}
