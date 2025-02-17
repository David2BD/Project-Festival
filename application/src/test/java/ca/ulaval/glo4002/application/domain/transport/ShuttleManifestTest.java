package ca.ulaval.glo4002.application.domain.transport;

import ca.ulaval.glo4002.application.domain.transport.manifest.ShuttleManifest;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;
import ca.ulaval.glo4002.application.domain.transport.types.TransportType;
import ca.ulaval.glo4002.application.domain.MoneyAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ShuttleManifestTest {
    private final LocalDate VALID_EVENT_DATE_1 = LocalDate.of(2060, 7, 17);
    private final LocalDate VALID_EVENT_DATE_2 = LocalDate.of(2060, 7, 18);
    private final MoneyAmount MILLENNIUM_FALCON_COST = new MoneyAmount(65000);
    @Mock
    private ShuttleType shuttleTypeMock;

    @Mock
    private Transport departureTransportMock;

    @Mock
    private Transport arrivalTransportMock;

    @Mock
    private Transport invalidTransportMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(departureTransportMock.getTransportType()).thenReturn(TransportType.DEPARTURE);
        when(arrivalTransportMock.getTransportType()).thenReturn(TransportType.ARRIVAL);
        when(invalidTransportMock.getTransportType()).thenThrow(
                new IllegalArgumentException("Invalid transport type: expected DEPARTURE or ARRIVAL"));
    }

    @Test
    void givenMapOfLocalDateAndTransportList_whenGeneratingManifest_thenCreatesManifestWithSeparatedArrivalsAndDepartures() {
        Map<LocalDate, List<Transport>> allTransports = new HashMap<>();
        allTransports.put(VALID_EVENT_DATE_1, List.of(departureTransportMock, arrivalTransportMock));

        ShuttleManifest shuttleManifest = new ShuttleManifest(allTransports);

        assertEquals(List.of(arrivalTransportMock), shuttleManifest.arrivals());
        assertEquals(List.of(departureTransportMock), shuttleManifest.departures());
    }

    @Test
    void givenMapOfMultipleLocalDatesAndTransportLists_whenGeneratingManifest_thenCreatesManifestWithSeparatedArrivalsAndDepartures() {
        Map<LocalDate, List<Transport>> allTransports = new HashMap<>();
        allTransports.put(VALID_EVENT_DATE_1, List.of(departureTransportMock));
        allTransports.put(VALID_EVENT_DATE_2, List.of(arrivalTransportMock));

        ShuttleManifest shuttleManifest = new ShuttleManifest(allTransports);

        assertEquals(List.of(arrivalTransportMock), shuttleManifest.arrivals());
        assertEquals(List.of(departureTransportMock), shuttleManifest.departures());
    }

    @Test
    void givenEmptyMapOfLocalDateAndTransportList_whenGeneratingManifest_thenCreatesEmptyManifest() {
        Map<LocalDate, List<Transport>> allTransports = new HashMap<>();

        ShuttleManifest shuttleManifest = new ShuttleManifest(allTransports);

        assertTrue(shuttleManifest.arrivals().isEmpty());
        assertTrue(shuttleManifest.departures().isEmpty());
    }

    @Test
    void givenMapOfLocalDateAndEmptyTransportList_whenGeneratingManifest_thenCreatesEmptyManifest() {
        Map<LocalDate, List<Transport>> allTransports = new HashMap<>();
        allTransports.put(VALID_EVENT_DATE_1, new ArrayList<>());

        ShuttleManifest shuttleManifest = new ShuttleManifest(allTransports);

        assertTrue(shuttleManifest.arrivals().isEmpty());
        assertTrue(shuttleManifest.departures().isEmpty());
    }

    @Test
    void givenInvalidTransportType_whenConstructingShuttleManifest_thenThrowIllegalArgumentException() {
        Map<LocalDate, List<Transport>> dateTransportMap = Map.of(LocalDate.now(), List.of(invalidTransportMock));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new ShuttleManifest(dateTransportMap));
        assertEquals("Invalid transport type: expected DEPARTURE or ARRIVAL", exception.getMessage());
    }

    @Test
    void givenValidTransportTypes_whenConstructingShuttleManifest_thenDoNotThrow() {
        Map<LocalDate, List<Transport>> dateTransportMap =
                Map.of(LocalDate.now(), List.of(departureTransportMock, arrivalTransportMock));

        assertDoesNotThrow(() -> new ShuttleManifest(dateTransportMap));
    }

    @Test
    void givenEmptyMap_whenCalculatingTotalTransportsCost_thenReturnZero() {
        MoneyAmount ZERO = new MoneyAmount(0);
        HashMap<LocalDate, List<Transport>> emptyMap = new HashMap<>();
        ShuttleManifest shuttleManifest = new ShuttleManifest(emptyMap);

        MoneyAmount totalTransportsCost = shuttleManifest.calculateTotalTransportsCost();

        assertTrue(totalTransportsCost.equals(ZERO));
    }

    @Test
    void givenMapWithTransports_whenCalculatingTotalTransportsCost_thenReturnSumOfAllTransportPrices() {
        when(departureTransportMock.getShuttleType()).thenReturn(shuttleTypeMock);
        when(arrivalTransportMock.getShuttleType()).thenReturn(shuttleTypeMock);
        when(shuttleTypeMock.getPrice()).thenReturn(MILLENNIUM_FALCON_COST);
        when(shuttleTypeMock.getPrice()).thenReturn(MILLENNIUM_FALCON_COST);
        Map<LocalDate, List<Transport>> dateTransportMap = Map.of(LocalDate.now(), List.of(departureTransportMock, arrivalTransportMock));
        ShuttleManifest shuttleManifest = new ShuttleManifest(dateTransportMap);
        MoneyAmount expectedTotalPrice = MILLENNIUM_FALCON_COST.add(MILLENNIUM_FALCON_COST);

        MoneyAmount totalTransportsCost = shuttleManifest.calculateTotalTransportsCost();

        assertTrue(totalTransportsCost.equals(expectedTotalPrice));
    }

}
