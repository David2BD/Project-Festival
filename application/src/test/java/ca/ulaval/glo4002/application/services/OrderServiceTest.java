package ca.ulaval.glo4002.application.services;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import ca.ulaval.glo4002.application.domain.festival.OrderNotFoundException;
import ca.ulaval.glo4002.application.domain.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private static final Long ORDER_NUMBER = 123456789L;
    private static final Long EXISTENT_ORDER_NUMBER = 456L;
    private static final Long NON_EXISTENT_ORDER_NUMBER = 123L;
    @Mock
    private Order existingOrderMock;

    @Mock
    private Festival festivalMock;

    @Mock
    private Order orderMock;

    @Mock
    private FestivalRepository festivalRepositoryMock;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        when(festivalRepositoryMock.getFestival()).thenReturn(festivalMock);

        orderService = new OrderService(festivalRepositoryMock);

    }

    @Test
    void givenOrder_whenCreateOrder_thenOrderIsSaved() {
        orderService.createOrder(orderMock);

        verify(festivalMock).addOrder(orderMock);
    }

    @Test
    void givenOrderNumber_whenGetOrder_thenReturnsOrderFromStorage() {
        when(festivalMock.getOrder(ORDER_NUMBER)).thenReturn(existingOrderMock);

        Order retrievedOrder = orderService.getOrder(ORDER_NUMBER);

        verify(festivalMock).getOrder(ORDER_NUMBER);
        assertEquals(existingOrderMock, retrievedOrder);
    }

    @Test
    void givenNonExistentOrderNumber_whenGetOrder_thenThrowOrderNotFoundException() {
        when(festivalMock.getOrder(NON_EXISTENT_ORDER_NUMBER)).thenThrow(
                new OrderNotFoundException("order with number " + NON_EXISTENT_ORDER_NUMBER + " not found"));

        OrderNotFoundException exception =
                assertThrows(OrderNotFoundException.class, () -> orderService.getOrder(NON_EXISTENT_ORDER_NUMBER));
        assertEquals("order with number 123 not found", exception.getMessage());
    }

    @Test
    void givenExistentOrderNumber_whenGetOrder_thenReturnOrder() {
        when(festivalMock.getOrder(EXISTENT_ORDER_NUMBER)).thenReturn(existingOrderMock);

        Order retrievedOrder = orderService.getOrder(EXISTENT_ORDER_NUMBER);

        assertEquals(existingOrderMock, retrievedOrder);
        verify(festivalMock, times(1)).getOrder(EXISTENT_ORDER_NUMBER);
    }
}
