package ca.ulaval.glo4002.application.infrastructure.sqLite;

import ca.ulaval.glo4002.application.domain.order.Order;
import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.utils.DateUtils;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderMapperSQLite {

    public Order convertFromSQLite(OrderSQLite orderSQLite, List<Pass> passes) {
        Long orderNumber = orderSQLite.orderNumber();
        String vendorCode = orderSQLite.vendorCode();
        ZonedDateTime orderDate = DateUtils.parseToZonedDateTime(orderSQLite.orderDate());

        return new Order(orderNumber, orderDate, vendorCode, passes);
    }

    public OrderSQLite convertToSQLite(Order order) {
        Long orderNumber = order.getOrderNumber();
        String formattedOrderDate = DateUtils.formatZonedDateTime(order.getOrderDate());
        String vendorCode = order.getVendorCode();

        return new OrderSQLite(orderNumber, formattedOrderDate, vendorCode);
    }
}

