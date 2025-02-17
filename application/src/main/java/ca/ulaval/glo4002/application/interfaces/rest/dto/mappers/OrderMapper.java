package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.order.Order;
import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.OrderRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.PassRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.OrderResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.PassResponseDTO;
import ca.ulaval.glo4002.application.utils.DateUtils;
import jakarta.inject.Inject;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderMapper {

    private final PassMapper passMapper;

    @Inject
    public OrderMapper(PassMapper passMapper) {
        this.passMapper = passMapper;
    }

    public Order toOrder(OrderRequestDTO orderRequestDTO) {
        String orderDate = orderRequestDTO.orderDate();
        String vendorCode = orderRequestDTO.vendorCode();
        List<PassRequestDTO> passRequestDTOS = orderRequestDTO.passes();
        List<Pass> passes = passMapper.toPasses(passRequestDTOS);
        ZonedDateTime parsedOrderDate = DateUtils.parseToZonedDateTime(orderDate);
        return new Order(parsedOrderDate, vendorCode, passes);
    }

    public OrderResponseDTO toOrderResponseDTO(Order order) {
        List<PassResponseDTO> passResponseDTOs = passMapper.toPassResponseDTOs(order.getPasses());
        return new OrderResponseDTO(order.calculateOrderPrice().twoDecimals(), passResponseDTOs);
    }
}

