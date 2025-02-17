package ca.ulaval.glo4002.application.domain.transport;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.transport.types.PassengerType;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;
import ca.ulaval.glo4002.application.domain.transport.types.TransportType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Transport {
    private final LocalDate eventDate;
    private final TransportType transportType;
    private final PassengerType passengerType;
    private final List<PassNumber> passNumbers;

    protected Transport(LocalDate eventDate, TransportType transportType, PassengerType passengerType) {
        this.eventDate = eventDate;
        this.transportType = transportType;
        this.passengerType = passengerType;
        this.passNumbers = new ArrayList<>();
    }

    protected Transport(
            LocalDate eventDate, TransportType transportType, PassengerType passengerType, List<PassNumber> passNumbers
    ) {
        this.eventDate = eventDate;
        this.transportType = transportType;
        this.passengerType = passengerType;
        this.passNumbers = passNumbers;
    }

    public abstract boolean isFull();

    public abstract ShuttleType getShuttleType();

    public boolean transportIsCompatibleWith(
            ShuttleType shuttleType, TransportType transportType, PassengerType passengerType
    ) {
        return (this.transportType == transportType) &&
               (this.getShuttleType() == shuttleType) &&
               (this.passengerType == passengerType) &&
               ! isFull();
    }

    public void addPassenger(PassNumber passNumber) {
        passNumbers.add(passNumber);
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public List<PassNumber> getPassNumbers() {
        return this.passNumbers;
    }

    public LocalDate getEventDate() {
        return this.eventDate;
    }
}
