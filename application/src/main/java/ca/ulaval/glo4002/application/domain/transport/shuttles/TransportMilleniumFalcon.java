package ca.ulaval.glo4002.application.domain.transport.shuttles;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.transport.Transport;
import ca.ulaval.glo4002.application.domain.transport.types.PassengerType;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;
import ca.ulaval.glo4002.application.domain.transport.types.TransportType;

import java.time.LocalDate;
import java.util.List;

public class TransportMilleniumFalcon extends Transport {
    private static final int MAX_SIZE = 20;

    public TransportMilleniumFalcon(LocalDate eventDate, TransportType transportType, PassengerType passengerType) {
        super(eventDate, transportType, passengerType);
    }

    public TransportMilleniumFalcon(
            LocalDate eventDate, TransportType transportType, PassengerType passengerType, List<PassNumber> passNumbers
    ) {
        super(eventDate, transportType, passengerType, passNumbers);
    }

    @Override
    public boolean isFull() {
        return super.getPassNumbers().size() >= MAX_SIZE;
    }

    @Override
    public ShuttleType getShuttleType() {
        return ShuttleType.MILLENNIUM_FALCON;
    }
}
