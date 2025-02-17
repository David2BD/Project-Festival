package ca.ulaval.glo4002.application.domain.transport.transportable;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.transport.types.PassengerType;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;

import java.time.LocalDate;
import java.util.List;

public abstract class Transportable {
    LocalDate dateArrival;
    LocalDate dateDeparture;

    protected Transportable(LocalDate dateArrival, LocalDate dateDeparture) {
        this.dateArrival = dateArrival;
        this.dateDeparture = dateDeparture;
    }

    public abstract ShuttleType getShuttleType();

    public abstract List<PassNumber> getPassNumbers();

    public abstract PassengerType getPassengerType();

    public LocalDate getDateArrival() {
        return dateArrival;
    }

    public LocalDate getDateDeparture() {
        return dateDeparture;
    }
}
