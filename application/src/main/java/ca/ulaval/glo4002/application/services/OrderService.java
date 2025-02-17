package ca.ulaval.glo4002.application.services;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import ca.ulaval.glo4002.application.domain.order.Order;
import jakarta.inject.Inject;

public class OrderService {
    private final FestivalRepository festivalRepository;

    @Inject
    public OrderService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    public Long createOrder(Order order) {
        Festival festival = festivalRepository.getFestival();
        festival.addOrder(order);
        festivalRepository.saveFestival(festival);
        return order.getOrderNumber();
    }

    public Order getOrder(Long orderNumber) {
        Festival festival = festivalRepository.getFestival();
        return festival.getOrder(orderNumber);
    }

}

