package ca.ulaval.glo4002.application.domain.order;

import ca.ulaval.glo4002.application.domain.pass.Pass;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderFixture {

    private ZonedDateTime orderDate = ZonedDateTime.of(2060, 5, 21, 15, 23, 20, 142, ZoneId.of("GMT"));
    private String vendorCode = "TEAM";
    private final List<Pass> passes = new ArrayList<>();
    long orderNumber = 12345L;

    public Order create() {
        return new Order(orderNumber, this.orderDate, this.vendorCode, this.passes);
    }

    public OrderFixture withOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public OrderFixture withVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public OrderFixture withOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }
}