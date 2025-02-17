package ca.ulaval.glo4002.application.domain.oxygen;

import ca.ulaval.glo4002.application.domain.order.Order;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class OxygenRequesterLister {

    public List<OxygenRequester> createListFromOrders(List<Order> orders) {
        List<OxygenRequester> oxygenRequesters = new ArrayList<>();
        for (Order order : orders) {
            oxygenRequesters.addAll(order.createOxygenRequesterList());
        }
        return oxygenRequesters;
    }

    public List<OxygenRequester> createListFromOrdersBeforeDate(List<Order> orders, LocalDateTime filterDate) {
        List<OxygenRequester> allOxygenRequestersFromOrders = createListFromOrders(orders);
        return listOxygenRequestersBeforeDate(allOxygenRequestersFromOrders, filterDate);
    }

    public List<OxygenRequester> listOxygenRequestersBeforeDate(
            List<OxygenRequester> oxygenRequesters, LocalDateTime filterDate
    ) {
        return oxygenRequesters.stream()
                .filter(oxygenRequester -> oxygenRequester.getRequestDate().isBefore(filterDate) ||
                                           oxygenRequester.getRequestDate().isEqual(filterDate))
                .collect(Collectors.toList());
    }

    public List<OxygenRequester> createListFromConfirmedProgram(
            HashMap<LocalDate, Artist> artistSchedule, LocalDateTime confirmationDate
    ) {
        List<OxygenRequester> oxygenRequesters = new ArrayList<>(artistSchedule.values());

        for (OxygenRequester oxygenRequester : oxygenRequesters) {
            oxygenRequester.setRequestDate(confirmationDate);
        }

        return oxygenRequesters;
    }
}
