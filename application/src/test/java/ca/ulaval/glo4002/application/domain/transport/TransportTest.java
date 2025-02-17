package ca.ulaval.glo4002.application.domain.transport;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.transport.shuttles.TransportETSpaceship;
import ca.ulaval.glo4002.application.domain.transport.shuttles.TransportMilleniumFalcon;
import ca.ulaval.glo4002.application.domain.transport.shuttles.TransportSpaceX;
import ca.ulaval.glo4002.application.domain.transport.types.PassengerType;
import ca.ulaval.glo4002.application.domain.transport.types.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransportTest {
    private static final LocalDate EVENT_DATE = LocalDate.of(2060, 7, 17);
    private static final PassengerType A_PASSENGER_TYPE = PassengerType.VISITOR;
    private Transport transportET;
    private Transport transportMF;
    private Transport transportX;
    @Mock
    private PassNumber mockPassNumber;

    @BeforeEach
    public void setUp() {
        transportET = new TransportETSpaceship(EVENT_DATE, TransportType.DEPARTURE, A_PASSENGER_TYPE);
        transportMF = new TransportMilleniumFalcon(EVENT_DATE, TransportType.DEPARTURE, A_PASSENGER_TYPE);
        transportX = new TransportSpaceX(EVENT_DATE, TransportType.DEPARTURE, A_PASSENGER_TYPE);
    }

    @Test
    void givenSpaceXShuttle_whenPassengersLessThan30_thenIsFullReturnsFalse() {
        for (int i = 0; i < 29; i++) {
            transportX.addPassenger(mockPassNumber);
        }

        assertFalse(transportX.isFull());
    }

    @Test
    void givenSpaceXShuttle_whenPassengersEquals30_thenIsFullReturnsTrue() {
        for (int i = 0; i < 30; i++) {
            transportX.addPassenger(mockPassNumber);
        }

        assertTrue(transportX.isFull());
    }

    @Test
    void givenMillenniumFalcon_whenPassengersLessThan20_thenIsFullReturnsFalse() {
        for (int i = 0; i < 19; i++) {
            transportX.addPassenger(mockPassNumber);
        }

        assertFalse(transportX.isFull());
    }

    @Test
    void givenMillenniumFalcon_whenPassengersEquals20_thenIsFullReturnsTrue() {
        for (int i = 0; i < 20; i++) {
            transportMF.addPassenger(mockPassNumber);
        }

        assertTrue(transportMF.isFull());
    }

    @Test
    void givenEtSpaceship_whenNoPassengerAdded_thenIsFullReturnsFalse() {
        assertFalse(transportET.isFull());
    }

    @Test
    void givenEtSpaceship_whenPassengerAdded_thenIsFullReturnsTrue() {
        transportET.addPassenger(mockPassNumber);

        assertTrue(transportET.isFull());
    }
}

