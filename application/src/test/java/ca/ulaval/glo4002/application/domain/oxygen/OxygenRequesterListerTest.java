package ca.ulaval.glo4002.application.domain.oxygen;

import ca.ulaval.glo4002.application.domain.order.Order;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OxygenRequesterListerTest {
    private final List<Order> orders = new ArrayList<>();
    private final List<OxygenRequester> oxygenRequesters = new ArrayList<>();
    private static final LocalDateTime FILTER_DATE = LocalDateTime.now();
    private OxygenRequesterLister oxygenRequesterLister;
    private final HashMap<LocalDate, Artist> confirmedProgram = new HashMap<>();

    @Mock
    Order orderMock;

    @Mock
    OxygenRequester oxygenRequesterMock;

    @Mock
    Artist artistMock;

    @BeforeEach
    void setUp() {
        oxygenRequesterLister = new OxygenRequesterLister();
        oxygenRequesters.add(oxygenRequesterMock);
    }

    @Test
    void givenListOfOrders_whenCreateListFromOrders_thenReturnListOfOxygenRequesters() {
        orders.add(orderMock);
        when(orderMock.createOxygenRequesterList()).thenReturn(oxygenRequesters);

        List<OxygenRequester> result = oxygenRequesterLister.createListFromOrders(orders);

        assertNotNull(result);
    }

    @Test
    void givenEmptyListOfOrders_whenCreateListFromOrders_thenReturnEmptyListOfOxygenRequesters() {
        List<OxygenRequester> result = oxygenRequesterLister.createListFromOrders(orders);

        assertTrue(result.isEmpty());
    }

    @Test
    void givenListOfOrders_whenCreateListFromOrders_thenCreateOxygenRequesterListIsCalled() {
        orders.add(orderMock);
        oxygenRequesterLister.createListFromOrders(orders);

        verify(orderMock).createOxygenRequesterList();
    }

    @Test
    void givenOxygenRequesterWithRequestDateAfterFilterDate_whenListOxygenRequestersBeforeDate_thenReturnOxygenRequesterNotInReturnedList() {
        LocalDateTime DATE_AFTER_FILTER_DATE = FILTER_DATE.plusDays(1);
        when(oxygenRequesterMock.getRequestDate()).thenReturn(DATE_AFTER_FILTER_DATE);

        List<OxygenRequester> result =
                oxygenRequesterLister.listOxygenRequestersBeforeDate(oxygenRequesters, FILTER_DATE);

        assertFalse(result.contains(oxygenRequesterMock));
    }

    @Test
    void givenOxygenRequesterWithRequestDateEqualToFilterDate_whenListOxygenRequestersBeforeDate_thenOxygenRequesterInReturnedList() {
        when(oxygenRequesterMock.getRequestDate()).thenReturn(FILTER_DATE);

        List<OxygenRequester> result =
                oxygenRequesterLister.listOxygenRequestersBeforeDate(oxygenRequesters, FILTER_DATE);

        assertTrue(result.contains(oxygenRequesterMock));
    }

    @Test
    void givenOxygenRequesterWithRequestDateBeforeFilterDate_whenListOxygenRequestersBeforeDate_thenOxygenRequesterInReturnedList() {
        LocalDateTime DATE_BEFORE_FILTER_DATE = FILTER_DATE.minusDays(1);
        when(oxygenRequesterMock.getRequestDate()).thenReturn(DATE_BEFORE_FILTER_DATE);

        List<OxygenRequester> result =
                oxygenRequesterLister.listOxygenRequestersBeforeDate(oxygenRequesters, FILTER_DATE);

        assertTrue(result.contains(oxygenRequesterMock));
    }

    @Test
    void givenConfirmedProgram_whenCreateListFromConfirmedProgram_thenReturnsOxygenRequesters() {
        LocalDate A_PERFORMANCE_DATE = LocalDate.now();
        LocalDateTime A_CONFIRMATION_DATE = LocalDateTime.now();
        confirmedProgram.put(A_PERFORMANCE_DATE, artistMock);
        when(artistMock.getRequestDate()).thenReturn(A_CONFIRMATION_DATE);

        List<OxygenRequester> result =
                oxygenRequesterLister.createListFromConfirmedProgram(confirmedProgram, A_CONFIRMATION_DATE);

        assertTrue(result.contains(artistMock));
        assertEquals(A_CONFIRMATION_DATE, result.get(0).getRequestDate());
    }

}