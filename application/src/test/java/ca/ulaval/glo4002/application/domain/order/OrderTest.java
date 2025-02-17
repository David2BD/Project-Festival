package ca.ulaval.glo4002.application.domain.order;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import ca.ulaval.glo4002.application.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderTest {
    private static final ZonedDateTime VALID_ORDER_DATE = DateUtils.parseToZonedDateTime("2060-05-21T15:23:20.142Z");
    private static final String ORDER_VENDOR_CODE = "TEAM";
    public static final BigDecimal ORDER_PRICE_TOTAL = BigDecimal.valueOf(900000);
    private final ZonedDateTime ORDER_DATE_AT_THE_TIME_OF_BUYABLE_RANGE =
            ZonedDateTime.of(2060, 5, 25, 0, 0, 0, 0, ZoneId.of("GMT"));
    private MoneyAmount moneyAmount50000;
    private MoneyAmount moneyAmount100000;
    private MoneyAmount moneyAmount700000;
    private Order order;

    @Mock
    private Pass mockDailyPass1;
    @Mock
    private Pass mockDailyPass2;
    @Mock
    private Pass mockDailyPass3;
    @Mock
    private Pass mockEventPass;

    @BeforeEach
    void setUp() {
        order = new Order(VALID_ORDER_DATE, ORDER_VENDOR_CODE, new ArrayList<>());

        mockDailyPass1 = mock(Pass.class);
        mockDailyPass2 = mock(Pass.class);
        mockDailyPass3 = mock(Pass.class);
        mockEventPass = mock(Pass.class);

        moneyAmount50000 = new MoneyAmount(50000);
        moneyAmount100000 = new MoneyAmount(100000);
        moneyAmount700000 = new MoneyAmount(700000);
    }

    @Test
    void givenEmptyPassList_whenOrderIsCreated_thenOrderIsInitializedCorrectly() {
        assertEquals(VALID_ORDER_DATE, order.getOrderDate());
        assertEquals(ORDER_VENDOR_CODE, order.getVendorCode());
        assertTrue(order.getPasses().isEmpty());
    }

    @Test
    void givenExistingOrder_whenCreatingNewOrder_thenUniqueOrderIdIsGenerated() {
        Order newOrder = new Order(VALID_ORDER_DATE, ORDER_VENDOR_CODE, new ArrayList<>());

        assertNotEquals(order.getOrderNumber(), newOrder.getOrderNumber());
    }

    @Test
    void givenMixedDailyAndEventPasses_whenCalculatingPrice_thenPriceIsCalculatedCorrectly() {
        setMocksForMixedDailyAndEventPassesOrderPrice();

        List<Pass> passes = List.of(mockDailyPass1, mockDailyPass2, mockDailyPass3, mockEventPass);
        Order order = new Order(ORDER_DATE_AT_THE_TIME_OF_BUYABLE_RANGE, ORDER_VENDOR_CODE, passes);
        MoneyAmount orderPrice = order.calculateOrderPrice();

        assertEquals(ORDER_PRICE_TOTAL, orderPrice.getAmount());
    }

    private void setMocksForMixedDailyAndEventPassesOrderPrice() {
        when(mockDailyPass1.getPassPrice()).thenReturn(moneyAmount50000);
        when(mockDailyPass2.getPassPrice()).thenReturn(moneyAmount50000);
        when(mockDailyPass3.getPassPrice()).thenReturn(moneyAmount100000);
        when(mockEventPass.getPassPrice()).thenReturn(moneyAmount700000);
        when(mockDailyPass1.getPassCategory()).thenReturn(PassCategoryTypes.STANDARD);
        when(mockDailyPass2.getPassCategory()).thenReturn(PassCategoryTypes.STANDARD);
        when(mockDailyPass3.getPassCategory()).thenReturn(PassCategoryTypes.PREMIUM);
        when(mockDailyPass1.canBeUsedForDiscount()).thenReturn(true);
        when(mockDailyPass2.canBeUsedForDiscount()).thenReturn(true);
        when(mockDailyPass3.canBeUsedForDiscount()).thenReturn(true);
        when(mockEventPass.canBeUsedForDiscount()).thenReturn(false);
    }
}