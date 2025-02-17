package ca.ulaval.glo4002.application.domain.festival;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.order.Order;
import ca.ulaval.glo4002.application.domain.order.OrderDates;
import ca.ulaval.glo4002.application.domain.order.OrderFixture;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenReportGenerator;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenRequesterLister;
import ca.ulaval.glo4002.application.domain.pass.exceptions.InvalidEventDateException;
import ca.ulaval.glo4002.application.domain.reports.OxygenReport;
import ca.ulaval.glo4002.application.domain.reports.ProfitReport;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.ScheduleSimulation;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.ScheduleSimulator;
import ca.ulaval.glo4002.application.domain.transport.Transport;
import ca.ulaval.glo4002.application.domain.transport.manifest.ShuttleManifestGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FestivalTest {
    private Festival festival;

    private final String A_FESTIVAL_ID = "ABCDEF123456";
    private final String A_FESTIVAL_NAME = "UniversityFest";
    private final String A_VENDOR_CODE = "TEAM";
    private final ZonedDateTime AN_ORDER_DATE = ZonedDateTime.of(2060, 07, 17, 0, 0, 0, 0, ZoneId.of("GMT"));
    private final LocalDate ANY_FESTIVAL_START_DATE = LocalDate.of(2020, 1, 1);
    private final LocalDate ANY_DATE_BEFORE_FESTIVAL_START = LocalDate.of(2019, 12, 31);
    private final MoneyAmount A_PRICE = new MoneyAmount(65000);
    private final MoneyAmount OXYGEN_COST = new MoneyAmount(10000);
    private final MoneyAmount ZERO = new MoneyAmount(0);
    private final List<OxygenRequester> oxygenRequestersIncludingArtists = new ArrayList<>();
    private final List<OxygenRequester> oxygenRequestersExcludingArtists = new ArrayList<>();
    private final LocalDate A_PROGRAM_CONFIRMATION_DATE = LocalDate.of(2020, 1, 1);
    private final MoneyAmount A_TOTAL_ARTIST_COST = new MoneyAmount(10);

    @Mock
    private OxygenReportGenerator oxygenReportGeneratorMock;

    @Mock
    private ShuttleManifestGenerator shuttleManifestGeneratorMock;

    @Mock
    private ScheduleSimulator scheduleSimulatorMock;

    @Mock
    private ScheduleSimulation scheduleSimulationMock;

    @Mock
    private FestivalDates festivalDateMock;

    @Mock
    private OrderDates orderDatesMock;

    @Mock
    private OxygenRequesterLister oxygenRequesterListerMock;

    @Mock
    private OxygenRequester oxygenRequesterMock;

    @Mock
    private OxygenReport oxygenReportIncludingArtistsMock;

    @Mock
    private OxygenReport oxygenReportExcludingArtistsMock;

    @Mock
    private MoneyAmount costExcludingArtistsMock;

    @Mock
    private MoneyAmount costIncludingArtistsMock;

    @BeforeEach
    void setUp() {
        festival = new Festival(A_FESTIVAL_ID, A_FESTIVAL_NAME, festivalDateMock, orderDatesMock, A_VENDOR_CODE,
                                shuttleManifestGeneratorMock, oxygenReportGeneratorMock, scheduleSimulatorMock,
                                oxygenRequesterListerMock);
        oxygenRequestersIncludingArtists.add(oxygenRequesterMock);

        lenient().when(oxygenRequesterListerMock.listOxygenRequestersBeforeDate(anyList(), any(LocalDateTime.class)))
                .thenReturn(oxygenRequestersIncludingArtists);
        lenient().when(
                        oxygenReportGeneratorMock.generateOxygenReport(oxygenRequestersIncludingArtists, festivalDateMock))
                .thenReturn(oxygenReportIncludingArtistsMock);
        lenient().when(
                        oxygenRequesterListerMock.createListFromOrdersBeforeDate(any(List.class), any(LocalDateTime.class)))
                .thenReturn(oxygenRequestersExcludingArtists);
        lenient().when(
                        oxygenReportGeneratorMock.generateOxygenReport(oxygenRequestersExcludingArtists, festivalDateMock))
                .thenReturn(oxygenReportExcludingArtistsMock);

        lenient().when(oxygenRequesterListerMock.createListFromOrders(any(List.class)))
                .thenReturn(oxygenRequestersIncludingArtists);
        lenient().when(oxygenReportIncludingArtistsMock.calculateOxygenCost()).thenReturn(costIncludingArtistsMock);
        lenient().when(oxygenReportExcludingArtistsMock.calculateOxygenCost()).thenReturn(costExcludingArtistsMock);
        lenient().when(costIncludingArtistsMock.subtract(costExcludingArtistsMock)).thenReturn(A_TOTAL_ARTIST_COST);
        lenient().when(festivalDateMock.getStartDate()).thenReturn(ANY_FESTIVAL_START_DATE);
        lenient().when(scheduleSimulationMock.getSchedule()).thenReturn(new HashMap<>());
        lenient().when(scheduleSimulationMock.getTotalCostForArtists()).thenReturn(A_TOTAL_ARTIST_COST);
        lenient().when(scheduleSimulationMock.calculateCostForArtistsTransports()).thenReturn(new MoneyAmount(0));
    }

    @Test
    void givenNoOrder_whenFestivalIsCreated_thenOrdersListIsEmpty() {
        List<Order> orders = festival.getOrders();

        assertTrue(orders.isEmpty());
    }

    @Test
    void givenNoOrder_whenGenerateShuttleManifest_thenReturnEmptyMap() {
        Map<LocalDate, List<Transport>> transports = festival.generateShuttleManifest(null);

        assertTrue(transports.isEmpty());
    }

    @Test
    void givenValidOrder_whenAddOrderToFestival_thenOrderListContainsTheOrder() {
        Order order = new OrderFixture().create();

        festival.addOrder(order);

        List<Order> ordersList = festival.getOrders();
        assertTrue(ordersList.contains(order));
    }

    @Test
    void givenAnOrder_whenAddOrderToFestival_thenOrderDatesVerifiesDates() {
        Order order = new OrderFixture().withOrderDate(AN_ORDER_DATE).create();

        festival.addOrder(order);

        verify(orderDatesMock).validateOrderDateIsInBuyableRange(AN_ORDER_DATE);
    }

    @Test
    void givenInvalidVendorCode_whenAddOrderToFestival_thenThrowsInvalidVendorCodeException() {
        Order order = new OrderFixture().withVendorCode("INVALID").create();

        assertThrows(InvalidVendorCodeException.class, () -> festival.addOrder(order));
    }

    @Test
    void whenGenerateOxygenReport_thenCreateOxygenReportIsCalled() {
        festival.getOxygenReport();

        verify(oxygenReportGeneratorMock).generateOxygenReport(anyList(), any(FestivalDates.class));
    }

    @Test
    void givenInvalidDate_whenGenerateShuttleManifest_thenThrowsException() {
        LocalDate invalidDate = LocalDate.of(2050, 1, 1);
        doThrow(InvalidEventDateException.class).when(festivalDateMock).verifyEventDate(invalidDate);

        assertThrows(InvalidEventDateException.class, () -> festival.generateShuttleManifest(invalidDate));
    }

    @Test
    void givenOrderNumberExists_whenGetOrder_thenReturnsOrder() {
        Order order = new OrderFixture().withOrderNumber(123L).create();
        festival.addOrder(order);

        Order fetchedOrder = festival.getOrder(123L);

        assertEquals(order, fetchedOrder);
    }

    @Test
    void givenOrderNumberDoesNotExist_whenGetOrder_thenThrowsInvalidOrderNumberException() {
        assertThrows(OrderNotFoundException.class, () -> festival.getOrder(999L));
    }

    @Test
    void givenFestivalWithNoOrders_whenGenerateProfitReport_thenAllProfitReportParametersAreZero() {
        OxygenReport oxygenReport = mock(OxygenReport.class);
        when(festival.getOxygenReport()).thenReturn(oxygenReport);
        when(oxygenReport.calculateOxygenCost()).thenReturn(ZERO);

        ProfitReport profitReport = festival.generateProfitReport();

        assertEquals(ZERO, profitReport.income());
        assertEquals(ZERO, profitReport.expenses());
        assertEquals(ZERO, profitReport.profit());
    }

    @Test
    void givenFestivalWithOrders_whenGenerateProfitReport_thenReturnsExpectedIncome() {
        Order orderMock1 = mock(Order.class);
        Order orderMock2 = mock(Order.class);
        OxygenReport oxygenReport = mock(OxygenReport.class);
        prepareFestivalWithOrderMocks(orderMock1, orderMock2, oxygenReport);
        MoneyAmount priceOrder1 = orderMock1.calculateOrderPrice();
        MoneyAmount priceOrder2 = orderMock2.calculateOrderPrice();
        MoneyAmount expectedIncome = priceOrder1.add(priceOrder2);

        ProfitReport profitReport = festival.generateProfitReport();

        assertTrue(expectedIncome.equals(profitReport.income()));
    }

    @Test
    void givenFestivalWithOrders_whenGenerateProfitReport_thenOxygenCostIsCalculated() {
        Order orderMock1 = mock(Order.class);
        Order orderMock2 = mock(Order.class);
        OxygenReport oxygenReport = mock(OxygenReport.class);
        prepareFestivalWithOrderMocks(orderMock1, orderMock2, oxygenReport);

        festival.generateProfitReport();

        verify(oxygenReport).calculateOxygenCost();
    }

    private void prepareFestivalWithOrderMocks(Order orderMock1, Order orderMock2, OxygenReport oxygenReport) {
        when(orderMock1.calculateOrderPrice()).thenReturn(A_PRICE);
        when(orderMock2.calculateOrderPrice()).thenReturn(A_PRICE);
        when(orderMock1.getOrderDate()).thenReturn(AN_ORDER_DATE);
        when(orderMock2.getOrderDate()).thenReturn(AN_ORDER_DATE);
        when(orderMock1.getVendorCode()).thenReturn(A_VENDOR_CODE);
        when(orderMock2.getVendorCode()).thenReturn(A_VENDOR_CODE);

        when(festival.getOxygenReport()).thenReturn(oxygenReport);
        when(oxygenReport.calculateOxygenCost()).thenReturn(OXYGEN_COST);

        festival.addOrder(orderMock1);
        festival.addOrder(orderMock2);
    }

    @Test
    void givenAlreadyConfirmedProgram_whenConfirmProgram_thenThrowsAlreadyConfirmedException() {
        LocalDate ANY_DATE = LocalDate.of(2020, 1, 1);
        festival.setProgramIsConfirmed(true);

        assertThrows(AlreadyConfirmedException.class, () -> festival.confirmProgram(scheduleSimulationMock, ANY_DATE));
    }

    @Test
    void givenNotConfirmedProgramAndDateAfterFestivalStartDate_whenConfirmProgram_thenThrowsInvalidConfirmationDateException() {
        LocalDate ANY_DATE_AFTER_FESTIVAL_START = LocalDate.of(2020, 1, 2);

        assertThrows(InvalidConfirmationDateException.class,
                     () -> festival.confirmProgram(scheduleSimulationMock, ANY_DATE_AFTER_FESTIVAL_START));
    }

    @Test
    void givenValidSchduleSimulationAndDate_whenConfirmProgram_thenScheduleSimulationGetScheduleIsCalled() {
        festival.confirmProgram(scheduleSimulationMock, ANY_DATE_BEFORE_FESTIVAL_START);

        verify(scheduleSimulationMock).getSchedule();
    }

    @Test
    void givenValidSchduleSimulationAndDate_whenConfirmProgram_thenScheduleSimulationGetTotalCostForArtistsIsCalled() {
        festival.confirmProgram(scheduleSimulationMock, ANY_DATE_BEFORE_FESTIVAL_START);

        verify(scheduleSimulationMock).getTotalCostForArtists();
    }

    @Test
    void givenValidSchduleSimulationAndDate_whenConfirmProgram_thenScheduleSimulationCalculateCostForArtistsTransportsIsCalled() {
        festival.confirmProgram(scheduleSimulationMock, ANY_DATE_BEFORE_FESTIVAL_START);

        verify(scheduleSimulationMock).calculateCostForArtistsTransports();
    }

    @Test
    void givenConfirmedProgram_whenCalculateOxygenCostForArtists_thenOxygenRequesterListerCalled() {
        festival.setConfirmationDate(A_PROGRAM_CONFIRMATION_DATE);
        festival.setProgramIsConfirmed(true);

        festival.calculateOxygenCostForArtists();

        verify(oxygenRequesterListerMock).listOxygenRequestersBeforeDate(oxygenRequestersIncludingArtists,
                                                                         festival.getProgramConfirmationDate());
        verify(oxygenRequesterListerMock).createListFromOrdersBeforeDate(any(List.class),
                                                                         eq(festival.getProgramConfirmationDate()));
    }

    @Test
    void givenConfirmedProgram_whenCalculateOxygenCostForArtists_thenOxygenReportGeneratorCalled() {
        festival.setConfirmationDate(A_PROGRAM_CONFIRMATION_DATE);
        festival.setProgramIsConfirmed(true);

        festival.calculateOxygenCostForArtists();

        verify(oxygenReportGeneratorMock).generateOxygenReport(oxygenRequestersIncludingArtists, festivalDateMock);
        verify(oxygenReportGeneratorMock).generateOxygenReport(oxygenRequestersExcludingArtists, festivalDateMock);
    }

    @Test
    void givenConfirmedProgram_whenCalculateOxygenCostForArtists_thenReturnsCost() {
        festival.setConfirmationDate(A_PROGRAM_CONFIRMATION_DATE);
        festival.setProgramIsConfirmed(true);

        MoneyAmount result = festival.calculateOxygenCostForArtists();

        verify(costIncludingArtistsMock).subtract(costExcludingArtistsMock);
        assertEquals(A_TOTAL_ARTIST_COST.getAmount(), result.getAmount());
    }

    @Test
    void givenNotConfirmedProgram_whenCalculateOxygenCostForArtists_thenReturnsZero() {
        MoneyAmount result = festival.calculateOxygenCostForArtists();
        MoneyAmount expected = new MoneyAmount(0);

        assertEquals(expected.getAmount(), result.getAmount());
    }
}








