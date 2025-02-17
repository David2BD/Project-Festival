package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.order.Order;
import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.OrderRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.PassRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.OrderResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.PassResponseDTO;
import ca.ulaval.glo4002.application.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    @Mock
    private PassMapper passMapper;

    @Mock
    private Pass passMock;

    @Mock
    private OrderRequestDTO orderRequestDTOMock;

    @Mock
    private PassResponseDTO passResponseDTOMock;

    @Mock
    private Order orderMock;

    @InjectMocks
    private OrderMapper orderMapper;

    private static final String AN_ORDER_DATE = "2060-05-13T10:15:30+00:00";
    private static final String A_VENDOR_CODE = "TEAM";
    private static final MoneyAmount A_PRICE = new MoneyAmount(150.0);

    @Test
    void givenOrderRequestDTO_whenToOrder_thenReturnsOrderWithSameAttributes() {
        ZonedDateTime expectedDate = DateUtils.parseToZonedDateTime(AN_ORDER_DATE);
        List<PassRequestDTO> passRequestDTOs = Collections.singletonList(mock(PassRequestDTO.class));
        List<Pass> passes = Collections.singletonList(passMock);
        when(orderRequestDTOMock.orderDate()).thenReturn(AN_ORDER_DATE);
        when(orderRequestDTOMock.vendorCode()).thenReturn(A_VENDOR_CODE);
        when(orderRequestDTOMock.passes()).thenReturn(passRequestDTOs);
        when(passMapper.toPasses(passRequestDTOs)).thenReturn(passes);

        Order result = orderMapper.toOrder(orderRequestDTOMock);

        assertEquals(expectedDate, result.getOrderDate());
        assertEquals(A_VENDOR_CODE, result.getVendorCode());
        assertEquals(passes, result.getPasses());
    }

    @Test
    void givenOrder_whenToOrderResponseDTO_thenReturnsOrderResponseDTOWithSameAttributes() {
        List<PassResponseDTO> passResponseDTOs = Collections.singletonList(passResponseDTOMock);
        List<Pass> passes = Collections.singletonList(passMock);
        when(orderMock.getPasses()).thenReturn(passes);
        when(orderMock.calculateOrderPrice()).thenReturn(A_PRICE);
        when(passMapper.toPassResponseDTOs(passes)).thenReturn(passResponseDTOs);

        OrderResponseDTO result = orderMapper.toOrderResponseDTO(orderMock);

        assertEquals(A_PRICE.twoDecimals(), result.orderPrice());
        assertEquals(passResponseDTOs, result.passes());
    }
}