package ca.ulaval.glo4002.application.domain.transport.transportable;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategory;
import ca.ulaval.glo4002.application.domain.transport.types.PassengerType;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransportableIndividualTest {

    private static final LocalDate ARRIVAL_DATE = LocalDate.of(2024, 7, 1);
    private static final LocalDate DEPARTURE_DATE = LocalDate.of(2024, 7, 10);
    private static final PassNumber PASS_NUMBER = new PassNumber(12345L);
    private static final int EXPECTED_PASS_COUNT = 1;
    private static final PassengerType EXPECTED_PASSENGER_TYPE = PassengerType.VISITOR;
    private static final ShuttleType EXPECTED_STANDARD_SHUTTLE = ShuttleType.SPACE_X;
    private static final ShuttleType EXPECTED_PREMIUM_SHUTTLE = ShuttleType.MILLENNIUM_FALCON;
    private static final ShuttleType EXPECTED_VIP_SHUTTLE = ShuttleType.ET_SPACESHIP;

    @Mock
    private PassCategory passCategoryMock;

    @InjectMocks
    private TransportableIndividual transportableIndividual;

    @BeforeEach
    void setUp() {
        transportableIndividual =
                new TransportableIndividual(ARRIVAL_DATE, DEPARTURE_DATE, passCategoryMock, PASS_NUMBER);
    }

    @Test
    void givenStandardPassCategory_whenGetShuttleType_thenReturnsSpaceX() {
        when(passCategoryMock.getShuttleType()).thenReturn(EXPECTED_STANDARD_SHUTTLE);

        ShuttleType shuttleType = transportableIndividual.getShuttleType();

        assertEquals(EXPECTED_STANDARD_SHUTTLE, shuttleType);
    }

    @Test
    void givenPremiumPassCategory_whenGetShuttleType_thenReturnsMillenniumFalcon() {
        when(passCategoryMock.getShuttleType()).thenReturn(EXPECTED_PREMIUM_SHUTTLE);

        ShuttleType shuttleType = transportableIndividual.getShuttleType();

        assertEquals(EXPECTED_PREMIUM_SHUTTLE, shuttleType);
    }

    @Test
    void givenVipPassCategory_whenGetShuttleType_thenReturnsETSpaceship() {
        when(passCategoryMock.getShuttleType()).thenReturn(EXPECTED_VIP_SHUTTLE);

        ShuttleType shuttleType = transportableIndividual.getShuttleType();

        assertEquals(EXPECTED_VIP_SHUTTLE, shuttleType);
    }

    @Test
    void givenTransportableIndividual_whenGetPassNumbers_thenReturnsCorrectPassNumber() {
        List<PassNumber> passNumbers = transportableIndividual.getPassNumbers();

        assertEquals(EXPECTED_PASS_COUNT, passNumbers.size());
        assertEquals(PASS_NUMBER, passNumbers.get(0));
    }

    @Test
    void givenTransportableIndividual_whenGetPassengerType_thenReturnsVisitor() {
        PassengerType passengerType = transportableIndividual.getPassengerType();

        assertEquals(EXPECTED_PASSENGER_TYPE, passengerType);
    }
}