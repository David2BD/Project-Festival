package ca.ulaval.glo4002.application.domain.transport;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.transport.shuttles.TransportETSpaceship;
import ca.ulaval.glo4002.application.domain.transport.shuttles.TransportMilleniumFalcon;
import ca.ulaval.glo4002.application.domain.transport.shuttles.TransportSpaceX;
import ca.ulaval.glo4002.application.domain.transport.types.PassengerType;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;
import ca.ulaval.glo4002.application.domain.transport.types.TransportType;

import java.time.LocalDate;
import java.util.List;

public class TransportFactory {
    public Transport createTransport(
            LocalDate eventDate, ShuttleType shuttleType, TransportType transportType, PassengerType passengerType
    ) {
        return switch (shuttleType) {
            case ET_SPACESHIP -> new TransportETSpaceship(eventDate, transportType, passengerType);
            case MILLENNIUM_FALCON -> new TransportMilleniumFalcon(eventDate, transportType, passengerType);
            case SPACE_X -> new TransportSpaceX(eventDate, transportType, passengerType);
        };
    }

    public Transport createTransportForArtist(
            LocalDate eventDate, ShuttleType shuttleType, TransportType transportType, PassengerType passengerType,
            List<PassNumber> passNumbers
    ) {
        return switch (shuttleType) {
            case ET_SPACESHIP -> new TransportETSpaceship(eventDate, transportType, passengerType, passNumbers);
            case MILLENNIUM_FALCON ->
                    new TransportMilleniumFalcon(eventDate, transportType, passengerType, passNumbers);
            case SPACE_X -> new TransportSpaceX(eventDate, transportType, passengerType, passNumbers);
        };
    }
}
