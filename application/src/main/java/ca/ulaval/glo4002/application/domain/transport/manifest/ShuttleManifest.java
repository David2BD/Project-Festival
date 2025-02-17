package ca.ulaval.glo4002.application.domain.transport.manifest;

import ca.ulaval.glo4002.application.domain.transport.Transport;
import ca.ulaval.glo4002.application.domain.transport.types.TransportType;

import ca.ulaval.glo4002.application.domain.MoneyAmount;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShuttleManifest {

    private final Map<TransportType, List<Transport>> shuttleManifest;

    public ShuttleManifest(Map<LocalDate, List<Transport>> dateTransportsListHashMap) {
        this.shuttleManifest =
                Map.of(TransportType.DEPARTURE, new ArrayList<>(), TransportType.ARRIVAL, new ArrayList<>());
        List<Transport> departuresList = new ArrayList<>();
        List<Transport> arrivalsList = new ArrayList<>();

        for (Map.Entry<LocalDate, List<Transport>> dateTransportEntry : dateTransportsListHashMap.entrySet()) {
            List<Transport> transports = dateTransportEntry.getValue();
            if (transports != null) {
                for (Transport transport : transports) {
                    switch (transport.getTransportType()) {
                        case DEPARTURE -> departuresList.add(transport);
                        case ARRIVAL -> arrivalsList.add(transport);
                        default ->
                                throw new IllegalArgumentException("Invalid transport type: expected DEPARTURE or ARRIVAL");
                    }
                }
            }
        }

        this.shuttleManifest.get(TransportType.DEPARTURE).addAll(departuresList);
        this.shuttleManifest.get(TransportType.ARRIVAL).addAll(arrivalsList);
    }

    public MoneyAmount calculateTotalTransportsCost() {
        MoneyAmount totalTransportsCost = new MoneyAmount(0);
        for (Transport transport : this.shuttleManifest.get(TransportType.DEPARTURE)) {
            totalTransportsCost = totalTransportsCost.add(transport.getShuttleType().getPrice());
        }
        for (Transport transport : this.shuttleManifest.get(TransportType.ARRIVAL)) {
            totalTransportsCost = totalTransportsCost.add(transport.getShuttleType().getPrice());
        }
        return totalTransportsCost;
    }

    public List<Transport> arrivals() {
        return this.shuttleManifest.get(TransportType.ARRIVAL);
    }

    public List<Transport> departures() {
        return this.shuttleManifest.get(TransportType.DEPARTURE);
    }
}
