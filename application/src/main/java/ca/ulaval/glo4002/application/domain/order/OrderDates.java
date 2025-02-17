package ca.ulaval.glo4002.application.domain.order;

import ca.ulaval.glo4002.application.utils.DateUtils;

import java.time.ZonedDateTime;

public class OrderDates {

    private final ZonedDateTime orderOpeningDate;
    private final ZonedDateTime orderClosingDate;

    public OrderDates(ZonedDateTime orderOpeningDate, ZonedDateTime orderClosingDate) {
        this.orderOpeningDate = orderOpeningDate;
        this.orderClosingDate = orderClosingDate;
    }

    public ZonedDateTime getOrderOpeningDate() {
        return this.orderOpeningDate;
    }

    public ZonedDateTime getOrderClosingDate() {
        return this.orderClosingDate;
    }

    public void validateOrderDateIsInBuyableRange(ZonedDateTime orderDate) {
        if (orderDate.isBefore(this.orderOpeningDate) || orderDate.isAfter(this.orderClosingDate)) {
            throw new InvalidOrderDateException(
                    "order date should be between " + DateUtils.formatZonedDateTimeToReadableDate(orderOpeningDate) + " and " + DateUtils.formatZonedDateTimeToReadableDate(orderClosingDate));
        }
    }
}
