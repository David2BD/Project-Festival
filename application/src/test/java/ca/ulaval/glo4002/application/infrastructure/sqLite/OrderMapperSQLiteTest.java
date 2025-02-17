package ca.ulaval.glo4002.application.infrastructure.sqLite;

import ca.ulaval.glo4002.application.domain.order.Order;
import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import ca.ulaval.glo4002.application.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderMapperSQLiteTest {

    private static final Long ORDER_NUMBER = 1L;
    private static final String ORDER_DATE = "2060-05-21T15:23:20.142Z";
    private static final String VENDOR_CODE = "vendorCode";
    private static final List<Pass> PASSES = new ArrayList<>();
    private static final PassCategoryTypes PASS_CATEGORY_STANDARD = PassCategoryTypes.STANDARD;
    private static final PassCategoryTypes PASS_CATEGORY_VIP = PassCategoryTypes.VIP;
    private static final LocalDate EVENT_DATE = LocalDate.of(2060, 7, 21);
    private final OrderMapperSQLite orderMapperSQLite = new OrderMapperSQLite();

    @BeforeEach
    void setUp() {
        PASSES.clear();
        Pass pass1 = mock(Pass.class);
        Pass pass2 = mock(Pass.class);
        PassNumber mockPassNumber = mock(PassNumber.class);

        when(pass1.getPassCategory()).thenReturn(PASS_CATEGORY_STANDARD);
        when(pass1.getEventDate()).thenReturn(EVENT_DATE);
        when(pass1.getPassNumber()).thenReturn(mockPassNumber);

        when(pass2.getPassCategory()).thenReturn(PASS_CATEGORY_VIP);
        when(pass2.getEventDate()).thenReturn(EVENT_DATE);
        when(pass2.getPassNumber()).thenReturn(mockPassNumber);

        PASSES.add(pass1);
        PASSES.add(pass2);
    }

    @Test
    void givenOrderSQLite_whenConvertingToOrder_thenFieldsAreEqual() {
        OrderSQLite orderSQLite = new OrderSQLite(ORDER_NUMBER, ORDER_DATE, VENDOR_CODE);

        Order order = orderMapperSQLite.convertFromSQLite(orderSQLite, PASSES);

        assertEquals(ORDER_NUMBER, order.getOrderNumber());
        assertEquals(VENDOR_CODE, order.getVendorCode());
        assertEquals(DateUtils.parseToZonedDateTime(ORDER_DATE), order.getOrderDate());
        assertEquals(PASSES, order.getPasses());
    }

    @Test
    void givenOrder_whenConvertingToOrderSQLite_thenFieldsAreEqual() {
        Order order = new Order(ORDER_NUMBER, DateUtils.parseToZonedDateTime(ORDER_DATE), VENDOR_CODE, PASSES);

        OrderSQLite orderSQLite = orderMapperSQLite.convertToSQLite(order);

        assertEquals(ORDER_NUMBER, orderSQLite.orderNumber());
        assertEquals(ORDER_DATE, orderSQLite.orderDate());
        assertEquals(VENDOR_CODE, orderSQLite.vendorCode());
        assertEquals(PASSES, order.getPasses());
    }

    @Test
    void givenOrderWithPasses_whenConvertingToOrder_thenPassFieldsAreEqual() {
        OrderSQLite orderSQLite = new OrderSQLite(ORDER_NUMBER, ORDER_DATE, VENDOR_CODE);
        Order order = orderMapperSQLite.convertFromSQLite(orderSQLite, PASSES);

        assertEquals(PASS_CATEGORY_STANDARD, order.getPasses().get(0).getPassCategory());
        assertEquals(PASS_CATEGORY_VIP, order.getPasses().get(1).getPassCategory());
        assertEquals(EVENT_DATE, order.getPasses().get(0).getEventDate());
        assertEquals(EVENT_DATE, order.getPasses().get(1).getEventDate());
    }

    @Test
    void givenOrder_whenConvertingToAndFromOrderSQLite_thenFieldsAreUnchanged() {
        Order order = new Order(ORDER_NUMBER, DateUtils.parseToZonedDateTime(ORDER_DATE), VENDOR_CODE, PASSES);

        OrderSQLite orderSQLite = orderMapperSQLite.convertToSQLite(order);
        Order convertedOrder = orderMapperSQLite.convertFromSQLite(orderSQLite, PASSES);

        assertEquals(ORDER_NUMBER, convertedOrder.getOrderNumber());
        assertEquals(VENDOR_CODE, convertedOrder.getVendorCode());
        assertEquals(DateUtils.parseToZonedDateTime(ORDER_DATE), convertedOrder.getOrderDate());
        assertEquals(PASSES, convertedOrder.getPasses());
    }
}