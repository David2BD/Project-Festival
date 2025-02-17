package ca.ulaval.glo4002.application.domain.order;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.order.price.OrderPriceCalculator;
import ca.ulaval.glo4002.application.domain.oxygen.FestivalClient;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.domain.transport.transportable.TransportableIndividual;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final long orderNumber;
    private final ZonedDateTime orderDate;
    private final String vendorCode;
    private final List<Pass> passes;
    private final OrderPriceCalculator orderPriceCalculator;

    public Order(ZonedDateTime orderDate, String vendorCode, List<Pass> passes) {
        this.orderDate = orderDate;
        this.vendorCode = vendorCode;
        this.passes = passes;
        this.orderNumber = Math.abs(UUID.randomUUID().getMostSignificantBits());
        this.orderPriceCalculator = new OrderPriceCalculator();
    }

    public Order(
            long orderNumber, ZonedDateTime orderDate, String vendorCode, List<Pass> passes
    ) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.vendorCode = vendorCode;
        this.passes = passes;
        this.orderPriceCalculator = new OrderPriceCalculator();
    }

    public long getOrderNumber() {
        return this.orderNumber;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public List<Pass> getPasses() {
        return passes;
    }

    public MoneyAmount calculateOrderPrice() {
        return orderPriceCalculator.calculateTotalPrice(passes);
    }

    public List<TransportableIndividual> generateTransportables(
            FestivalDates festivalDates
    ) {
        List<TransportableIndividual> transportables = new ArrayList<>();
        for (Pass pass : this.passes) {
            transportables.add(pass.generateTransportable(festivalDates.getStartDate(), festivalDates.getEndDate()));
        }
        return transportables;
    }

    public List<OxygenRequester> createOxygenRequesterList() {
        List<OxygenRequester> oxygenRequesters = new ArrayList<>();
        for (Pass pass : passes) {
            FestivalClient festivalClient = pass.createFestivalClient();
            festivalClient.setRequestDate(orderDate.toLocalDateTime());
            oxygenRequesters.add(festivalClient);
        }
        return oxygenRequesters;
    }
}
