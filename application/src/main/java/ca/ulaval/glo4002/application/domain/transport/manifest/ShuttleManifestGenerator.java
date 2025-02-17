package ca.ulaval.glo4002.application.domain.transport.manifest;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.transport.Transport;
import ca.ulaval.glo4002.application.domain.transport.TransportFactory;
import ca.ulaval.glo4002.application.domain.transport.transportable.Transportable;
import ca.ulaval.glo4002.application.domain.transport.types.PassengerType;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;
import ca.ulaval.glo4002.application.domain.transport.types.TransportType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShuttleManifestGenerator {
    private final TransportFactory transportFactory;

    public ShuttleManifestGenerator() {
        transportFactory = new TransportFactory();
    }

    public Map<LocalDate, List<Transport>> generateShuttleManifest(
            List<Transportable> transportables, LocalDate dateManifest
    ) {
        Map<LocalDate, List<Transport>> transportsByDate;
        if (dateManifest == null) {
            transportsByDate = generateFullShuttleManifest(transportables);
        }
        else {
            transportsByDate = generateDailyShuttleManifest(transportables, dateManifest);
        }
        return transportsByDate;
    }

    private Map<LocalDate, List<Transport>> generateFullShuttleManifest(
            List<Transportable> transportables
    ) {
        Map<LocalDate, List<Transport>> transports;
        transports = generateTransports(transportables);
        return transports;
    }

    private Map<LocalDate, List<Transport>> generateDailyShuttleManifest(
            List<Transportable> transportables, LocalDate date
    ) {
        Map<LocalDate, List<Transport>> transports;
        transports = generateTransports(transportables);

        Map<LocalDate, List<Transport>> transportsByDate = new HashMap<>();
        List<Transport> transportsForDate = transports.get(date);
        transportsByDate.put(date, transportsForDate);

        return transportsByDate;
    }

    private Map<LocalDate, List<Transport>> generateTransports(List<Transportable> transportables) {
        Map<LocalDate, List<Transport>> transports;
        transports = new HashMap<>();
        for (Transportable transportable : transportables) {
            addTransport(transports, transportable);
        }
        return transports;
    }

    private void addTransport(Map<LocalDate, List<Transport>> transports, Transportable transportable) {
        if (transportable.getPassengerType() == PassengerType.VISITOR) {
            addPassengerInVisitorTransport(transports, transportable, TransportType.DEPARTURE,
                                           transportable.getDateDeparture());
            addPassengerInVisitorTransport(transports, transportable, TransportType.ARRIVAL,
                                           transportable.getDateArrival());
        }
        else if (transportable.getPassengerType() == PassengerType.ARTIST) {
            addGroupInANewTransport(transports, transportable, TransportType.DEPARTURE,
                                    transportable.getDateDeparture());
            addGroupInANewTransport(transports, transportable, TransportType.ARRIVAL, transportable.getDateArrival());
        }
    }

    private void addGroupInANewTransport(
            Map<LocalDate, List<Transport>> transports, Transportable transportable, TransportType transportType,
            LocalDate date
    ) {
        List<Transport> dailyTransports = transports.computeIfAbsent(date, key -> new ArrayList<>());

        Transport newTransport =
                transportFactory.createTransportForArtist(date, transportable.getShuttleType(), transportType,
                                                          PassengerType.ARTIST, transportable.getPassNumbers());
        dailyTransports.add(newTransport);
    }

    private void addPassengerInVisitorTransport(
            Map<LocalDate, List<Transport>> transports, Transportable transportable, TransportType transportType,
            LocalDate date
    ) {
        List<Transport> dailyTransports = transports.computeIfAbsent(date, key -> new ArrayList<>());

        for (Transport transport : dailyTransports) {
            if (transport.transportIsCompatibleWith(transportable.getShuttleType(), transportType,
                                                    transportable.getPassengerType())) {
                transport.addPassenger(transportable.getPassNumbers().get(0));
                return;
            }
        }

        Transport newTransport = createNewTransportForVisitor(date, transportable.getShuttleType(), transportType,
                                                              transportable.getPassNumbers().get(0));
        dailyTransports.add(newTransport);
    }

    private Transport createNewTransportForVisitor(
            LocalDate eventDate, ShuttleType shuttleType, TransportType transportType, PassNumber passNumber
    ) {
        Transport newTransport =
                transportFactory.createTransport(eventDate, shuttleType, transportType, PassengerType.VISITOR);
        newTransport.addPassenger(passNumber);
        return newTransport;
    }

}
