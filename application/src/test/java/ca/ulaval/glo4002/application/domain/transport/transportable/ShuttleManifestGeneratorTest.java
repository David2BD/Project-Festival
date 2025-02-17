package ca.ulaval.glo4002.application.domain.transport.transportable;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.transport.Transport;
import ca.ulaval.glo4002.application.domain.transport.manifest.ShuttleManifestGenerator;
import ca.ulaval.glo4002.application.domain.transport.types.PassengerType;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ShuttleManifestGeneratorTest {
    private static final LocalDate DATE_DEPARTURE = LocalDate.of(2024, 7, 1);
    private static final LocalDate DATE_ARRIVAL = LocalDate.of(2024, 7, 10);
    private static final PassNumber PASS_NUMBER_VISITOR = new PassNumber(1L);
    private static final PassNumber PASS_NUMBER_ARTIST = new PassNumber(2L);

    @Mock
    private Transportable visitorMock;

    @Mock
    private Transportable artistMock;

    @InjectMocks
    private ShuttleManifestGenerator shuttleManifestGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(visitorMock.getDateDeparture()).thenReturn(DATE_DEPARTURE);
        when(visitorMock.getDateArrival()).thenReturn(DATE_ARRIVAL);
        when(visitorMock.getPassengerType()).thenReturn(PassengerType.VISITOR);
        when(visitorMock.getShuttleType()).thenReturn(ShuttleType.SPACE_X);
        when(visitorMock.getPassNumbers()).thenReturn(List.of(PASS_NUMBER_VISITOR));

        when(artistMock.getDateDeparture()).thenReturn(DATE_DEPARTURE);
        when(artistMock.getDateArrival()).thenReturn(DATE_ARRIVAL);
        when(artistMock.getPassengerType()).thenReturn(PassengerType.ARTIST);
        when(artistMock.getShuttleType()).thenReturn(ShuttleType.MILLENNIUM_FALCON);
        when(artistMock.getPassNumbers()).thenReturn(List.of(PASS_NUMBER_ARTIST));
    }

    @Test
    void givenNullDateManifest_whenGenerateShuttleManifest_thenProcessesAllTransportables() {
        List<Transportable> transportables = List.of(visitorMock, artistMock);

        Map<LocalDate, List<Transport>> manifest =
                shuttleManifestGenerator.generateShuttleManifest(transportables, null);

        assertNotNull(manifest);
        assertTrue(manifest.containsKey(DATE_DEPARTURE));
        assertTrue(manifest.containsKey(DATE_ARRIVAL));
    }

    @Test
    void givenSpecificDateManifest_whenGenerateShuttleManifest_thenProcessesOnlyThatDate() {
        List<Transportable> transportables = List.of(visitorMock, artistMock);

        Map<LocalDate, List<Transport>> manifest =
                shuttleManifestGenerator.generateShuttleManifest(transportables, DATE_DEPARTURE);

        assertNotNull(manifest);
        assertTrue(manifest.containsKey(DATE_DEPARTURE));
        assertFalse(manifest.containsKey(DATE_ARRIVAL));
    }

    @Test
    void givenEmptyTransportables_whenGenerateShuttleManifest_thenReturnsEmptyManifest() {
        Map<LocalDate, List<Transport>> manifest = shuttleManifestGenerator.generateShuttleManifest(List.of(), null);

        assertNotNull(manifest);
        assertTrue(manifest.isEmpty());
    }

    @Test
    void givenMultipleVisitors_whenGenerateShuttleManifest_thenReusesTransport() {
        List<Transportable> transportables = List.of(visitorMock, visitorMock);
        final int EXPECTED_TRANSPORT_COUNT = 1;

        Map<LocalDate, List<Transport>> manifest =
                shuttleManifestGenerator.generateShuttleManifest(transportables, null);

        assertNotNull(manifest);
        assertTrue(manifest.containsKey(DATE_DEPARTURE));
        assertTrue(manifest.containsKey(DATE_ARRIVAL));
        assertEquals(EXPECTED_TRANSPORT_COUNT, manifest.get(DATE_DEPARTURE).size(),
                     "There should be exactly one transport for the departure date.");
        assertEquals(EXPECTED_TRANSPORT_COUNT, manifest.get(DATE_ARRIVAL).size(),
                     "There should be exactly one transport for the arrival date.");
    }

    @Test
    void givenMultipleArtists_whenGenerateShuttleManifest_thenEachGetsDedicatedTransport() {
        List<Transportable> transportables = List.of(artistMock, artistMock);
        final int EXPECTED_TRANSPORT_COUNT = 2;

        Map<LocalDate, List<Transport>> manifest =
                shuttleManifestGenerator.generateShuttleManifest(transportables, null);

        assertNotNull(manifest);
        assertTrue(manifest.containsKey(DATE_DEPARTURE));
        assertTrue(manifest.containsKey(DATE_ARRIVAL));
        assertEquals(EXPECTED_TRANSPORT_COUNT, manifest.get(DATE_DEPARTURE).size(),
                     "Each artist should have a separate transport for departure.");
        assertEquals(EXPECTED_TRANSPORT_COUNT, manifest.get(DATE_ARRIVAL).size(),
                     "Each artist should have a separate transport for arrival.");
    }

}
