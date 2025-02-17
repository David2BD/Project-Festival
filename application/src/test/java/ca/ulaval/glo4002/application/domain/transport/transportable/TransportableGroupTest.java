package ca.ulaval.glo4002.application.domain.transport.transportable;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;
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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransportableGroupTest {

    private static final LocalDate ARRIVAL_DATE = LocalDate.of(2024, 7, 1);
    private static final LocalDate DEPARTURE_DATE = LocalDate.of(2024, 7, 10);
    private static final String ARTIST_NAME = "SomeArtist";
    private static final int GROUP_SIZE = 3;
    private static final int LARGE_GROUP_SIZE = 5;
    private static final int SINGLE_MEMBER = 1;
    private static final String FORMATTED_PASS_NUMBER_1 = ARTIST_NAME + "-001";
    private static final String FORMATTED_PASS_NUMBER_2 = ARTIST_NAME + "-002";
    private static final String FORMATTED_PASS_NUMBER_3 = ARTIST_NAME + "-003";
    private static final PassengerType EXPECTED_PASSENGER_TYPE = PassengerType.ARTIST;
    private static final ShuttleType EXPECTED_LARGE_GROUP_SHUTTLE = ShuttleType.MILLENNIUM_FALCON;
    private static final ShuttleType EXPECTED_SINGLE_MEMBER_SHUTTLE = ShuttleType.ET_SPACESHIP;

    @Mock
    private Artist artistMock;

    @InjectMocks
    private TransportableGroup transportableGroup;

    @BeforeEach
    void setUp() {
        lenient().when(artistMock.getGroupPeopleCount()).thenReturn(GROUP_SIZE);
        lenient().when(artistMock.generatePassString(1)).thenReturn(FORMATTED_PASS_NUMBER_1);
        lenient().when(artistMock.generatePassString(2)).thenReturn(FORMATTED_PASS_NUMBER_2);
        lenient().when(artistMock.generatePassString(3)).thenReturn(FORMATTED_PASS_NUMBER_3);
    }

    @Test
    void givenTransportableGroupWithManyMembers_whenGetShuttleType_thenReturnsMillenniumFalcon() {
        when(artistMock.getGroupPeopleCount()).thenReturn(LARGE_GROUP_SIZE);

        TransportableGroup transportableGroup = new TransportableGroup(ARRIVAL_DATE, DEPARTURE_DATE, artistMock);

        assertEquals(EXPECTED_LARGE_GROUP_SHUTTLE, transportableGroup.getShuttleType());
    }

    @Test
    void givenTransportableGroupWithOneMember_whenGetShuttleType_thenReturnsETSpaceship() {
        when(artistMock.getGroupPeopleCount()).thenReturn(SINGLE_MEMBER);

        TransportableGroup transportableGroup = new TransportableGroup(ARRIVAL_DATE, DEPARTURE_DATE, artistMock);

        assertEquals(EXPECTED_SINGLE_MEMBER_SHUTTLE, transportableGroup.getShuttleType());
    }

    @Test
    void givenTransportableGroupWithMoreThanOneMember_whenGetPassNumbers_thenReturnsCorrectValues() {
        when(artistMock.getGroupPeopleCount()).thenReturn(GROUP_SIZE);
        when(artistMock.generatePassString(1)).thenReturn(FORMATTED_PASS_NUMBER_1);
        when(artistMock.generatePassString(2)).thenReturn(FORMATTED_PASS_NUMBER_2);
        when(artistMock.generatePassString(3)).thenReturn(FORMATTED_PASS_NUMBER_3);
        TransportableGroup transportableGroup = new TransportableGroup(ARRIVAL_DATE, DEPARTURE_DATE, artistMock);

        List<PassNumber> passNumbers = transportableGroup.getPassNumbers();

        assertEquals(GROUP_SIZE, passNumbers.size());
        assertEquals(FORMATTED_PASS_NUMBER_1, passNumbers.get(0).getNumber());
        assertEquals(FORMATTED_PASS_NUMBER_2, passNumbers.get(1).getNumber());
        assertEquals(FORMATTED_PASS_NUMBER_3, passNumbers.get(2).getNumber());
    }

    @Test
    void givenTransportableGroup_whenGetPassengerType_thenReturnsCorrectPassengerType() {
        assertEquals(EXPECTED_PASSENGER_TYPE, transportableGroup.getPassengerType());
    }
}

