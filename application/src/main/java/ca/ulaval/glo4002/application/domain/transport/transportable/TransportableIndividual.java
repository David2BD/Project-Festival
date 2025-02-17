package ca.ulaval.glo4002.application.domain.transport.transportable;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategory;
import ca.ulaval.glo4002.application.domain.transport.types.PassengerType;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransportableIndividual extends Transportable {
    List<PassNumber> passNumbers;
    PassCategory passCategory;

    public TransportableIndividual(
            LocalDate dateArrival, LocalDate dateDeparture, PassCategory passCategory, PassNumber passNumbers
    ) {
        super(dateArrival, dateDeparture);
        this.passCategory = passCategory;
        this.passNumbers = new ArrayList<>();
        this.passNumbers.add(passNumbers);
    }

    public ShuttleType getShuttleType() {
        return passCategory.getShuttleType();
    }

    public List<PassNumber> getPassNumbers() {
        return passNumbers;
    }

    public PassengerType getPassengerType() {
        return PassengerType.VISITOR;
    }
}
