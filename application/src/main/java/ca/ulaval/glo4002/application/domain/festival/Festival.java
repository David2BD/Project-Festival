package ca.ulaval.glo4002.application.domain.festival;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.order.Order;
import ca.ulaval.glo4002.application.domain.order.OrderDates;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenReportGenerator;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenRequesterLister;
import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.domain.reports.OxygenReport;
import ca.ulaval.glo4002.application.domain.reports.ProfitReport;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.ConfirmedProgram;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.ScheduleSimulation;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.ScheduleSimulator;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.ArtistSchedulingStrategy;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.selection.ArtistSelectionStrategy;
import ca.ulaval.glo4002.application.domain.transport.Transport;
import ca.ulaval.glo4002.application.domain.transport.manifest.ShuttleManifest;
import ca.ulaval.glo4002.application.domain.transport.manifest.ShuttleManifestGenerator;
import ca.ulaval.glo4002.application.domain.transport.transportable.Transportable;
import ca.ulaval.glo4002.application.domain.transport.transportable.TransportableGroup;
import ca.ulaval.glo4002.application.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Festival {

    private final String festivalID;
    private final String name;
    private final OrderDates orderDates;
    private final String vendorCode;
    private final ShuttleManifestGenerator shuttleManifestGenerator;
    private final OxygenReportGenerator oxygenReportGenerator;
    private final List<Order> orders;
    private final ScheduleSimulator scheduleSimulator;
    private final FestivalDates festivalDates;
    private final HashMap<LocalDate, Artist> confirmedProgram;
    private MoneyAmount artistsTotalCost;
    private boolean programIsConfirmed;
    private LocalDateTime programConfirmationDate;
    private final OxygenRequesterLister oxygenRequesterLister;

    public Festival(
            String festivalID, String name, FestivalDates festivalDates, OrderDates orderDates, String vendorCode
    ) {
        this.festivalID = festivalID;
        this.name = name;
        this.festivalDates = festivalDates;
        this.orderDates = orderDates;
        this.vendorCode = vendorCode;
        this.shuttleManifestGenerator = new ShuttleManifestGenerator();
        this.oxygenReportGenerator = new OxygenReportGenerator();
        this.orders = new ArrayList<>();
        this.scheduleSimulator = new ScheduleSimulator(festivalDates);
        this.confirmedProgram = new HashMap<>();
        this.artistsTotalCost = new MoneyAmount(0);
        this.programIsConfirmed = false;
        this.oxygenRequesterLister = new OxygenRequesterLister();
    }

    public Festival(
            String festivalID, String name, FestivalDates festivalDates, OrderDates orderDates, String vendorCode,
            ShuttleManifestGenerator shuttleManifestGenerator, OxygenReportGenerator oxygenReportGenerator,
            ScheduleSimulator scheduleSimulator, OxygenRequesterLister oxygenRequesterLister
    ) {
        this.festivalID = festivalID;
        this.name = name;
        this.festivalDates = festivalDates;
        this.orderDates = orderDates;
        this.vendorCode = vendorCode;
        this.shuttleManifestGenerator = shuttleManifestGenerator;
        this.oxygenReportGenerator = oxygenReportGenerator;
        this.orders = new ArrayList<>();
        this.scheduleSimulator = scheduleSimulator;
        this.confirmedProgram = new HashMap<>();
        this.programIsConfirmed = false;
        this.oxygenRequesterLister = oxygenRequesterLister;
    }

    public void addOrder(Order order) {
        verifyOrderIsValidWithFestival(order);
        this.orders.add(order);
    }

    private void verifyOrderIsValidWithFestival(Order order) {
        orderDates.validateOrderDateIsInBuyableRange(order.getOrderDate());
        verifyVendorCode(order.getVendorCode());
        for (Pass pass : order.getPasses()) {
            verifyEventDateIsInFestivalDates(pass.getEventDate());
        }
    }

    private void verifyVendorCode(String vendorCode) {
        if (! Objects.equals(vendorCode, this.vendorCode)) {
            throw new InvalidVendorCodeException(String.format("Vendor code must be %s", this.vendorCode));
        }
    }

    private void verifyEventDateIsInFestivalDates(LocalDate eventDate) {
        this.festivalDates.verifyEventDate(eventDate);
    }

    public Map<LocalDate, List<Transport>> generateShuttleManifest(LocalDate date) {
        verifyEventDateIsInFestivalDates(date);
        Map<LocalDate, List<Transport>> transportsByDate;
        List<Transportable> transportables = generateTransportablesFromOrders();
        transportables.addAll(generateTransportablesFromProgram(confirmedProgram));
        transportsByDate = shuttleManifestGenerator.generateShuttleManifest(transportables, date);
        return transportsByDate;
    }

    private List<Transportable> generateTransportablesFromProgram(Map<LocalDate, Artist> confirmedProgram) {
        List<Transportable> transportables = new ArrayList<>();
        if (programIsConfirmed) {
            for (Map.Entry<LocalDate, Artist> entry : confirmedProgram.entrySet()) {
                LocalDate date = entry.getKey();
                Artist artist = entry.getValue();
                transportables.add(new TransportableGroup(date, date, artist));
            }
        }
        return transportables;
    }

    private List<Transportable> generateTransportablesFromOrders() {
        List<Transportable> transportables = new ArrayList<>();
        for (Order order : this.orders) {
            transportables.addAll(order.generateTransportables(this.festivalDates));
        }
        return transportables;
    }

    public OxygenReport getOxygenReport() {
        List<OxygenRequester> oxygenRequesters = listAllOxygenRequesters();

        return oxygenReportGenerator.generateOxygenReport(oxygenRequesters, festivalDates);

    }

    public MoneyAmount calculateOxygenCostForArtists() {
        if (programIsConfirmed) {
            List<OxygenRequester> oxygenRequestersIncludingArtists =
                    oxygenRequesterLister.listOxygenRequestersBeforeDate(listAllOxygenRequesters(),
                                                                         programConfirmationDate);
            OxygenReport oxygenReportIncludingArtists =
                    oxygenReportGenerator.generateOxygenReport(oxygenRequestersIncludingArtists, festivalDates);

            List<OxygenRequester> oxygenRequestersExcludingArtists =
                    oxygenRequesterLister.createListFromOrdersBeforeDate(orders, programConfirmationDate);

            OxygenReport oxygenReportExcludingArtists =
                    oxygenReportGenerator.generateOxygenReport(oxygenRequestersExcludingArtists, festivalDates);

            return oxygenReportIncludingArtists.calculateOxygenCost()
                    .subtract(oxygenReportExcludingArtists.calculateOxygenCost());
        }
        else {
            return new MoneyAmount(0);
        }
    }

    private List<OxygenRequester> listAllOxygenRequesters() {
        List<OxygenRequester> oxygenRequesters = new ArrayList<>();

        oxygenRequesters.addAll(createListFromArtistSchedule());
        oxygenRequesters.addAll(oxygenRequesterLister.createListFromOrders(orders));

        return oxygenRequesters;
    }

    private List<OxygenRequester> createListFromArtistSchedule() {
        if (! programIsConfirmed) {
            return new ArrayList<>();
        }

        return oxygenRequesterLister.createListFromConfirmedProgram(confirmedProgram, programConfirmationDate);
    }

    public String getId() {
        return this.festivalID;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public ScheduleSimulation getScheduleSimulation(
            List<Artist> artists, ArtistSelectionStrategy criteria, ArtistSchedulingStrategy schedulingType,
            MoneyAmount headlinerBudget, int headlinerLimit
    ) {
        return scheduleSimulator.simulateSchedule(artists, criteria, schedulingType, headlinerBudget, headlinerLimit);
    }

    public void confirmProgram(ScheduleSimulation scheduleSimulation, LocalDate confirmationDate) {
        if (programIsConfirmed) {
            throw new AlreadyConfirmedException("The program is already confirmed.");
        }
        if (!confirmationDate.isBefore(festivalDates.getStartDate())) {
            throw new InvalidConfirmationDateException(
                    "The confirmation date must be before the start of the festival");
        }

        this.confirmedProgram.putAll(scheduleSimulation.getSchedule());

        setProgramIsConfirmed(true);
        setConfirmationDate(confirmationDate);

        MoneyAmount artistsTotalCost = new MoneyAmount(0);

        artistsTotalCost = artistsTotalCost.add(scheduleSimulation.getTotalCostForArtists());
        artistsTotalCost = artistsTotalCost.add(scheduleSimulation.calculateCostForArtistsTransports());
        artistsTotalCost = artistsTotalCost.add(calculateOxygenCostForArtists());

        this.artistsTotalCost = artistsTotalCost;
    }

    public void setConfirmationDate(LocalDate date) {
        this.programConfirmationDate = DateUtils.toLocalDateTime(date);
    }

    public ConfirmedProgram getConfirmedProgram() {
        Map<LocalDate, Artist> schedule = new HashMap<>(confirmedProgram);

        ScheduleSimulation scheduleSimulation = new ScheduleSimulation(schedule);

        MoneyAmount totalCostForArtists = scheduleSimulation.getTotalCostForArtists();

        MoneyAmount transportCost = scheduleSimulation.calculateCostForArtistsTransports();
        MoneyAmount oxygenCost = calculateOxygenCostForArtists();
        MoneyAmount totalCost = transportCost.add(oxygenCost);

        return new ConfirmedProgram(schedule, totalCostForArtists, totalCost);
    }

    public Order getOrder(Long orderNumber) {
        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) return order;
        }
        throw new OrderNotFoundException("order with number " + orderNumber + " not found");
    }

    public LocalDate getFestivalStartDate() {
        return this.festivalDates.getStartDate();
    }

    public LocalDate getFestivalEndDate() {
        return this.festivalDates.getEndDate();
    }

    public ZonedDateTime getOrdersOpeningDate() {
        return this.orderDates.getOrderOpeningDate();
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getOrdersClosingDate() {
        return this.orderDates.getOrderClosingDate();
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public ProfitReport generateProfitReport() {
        MoneyAmount income = calculateIncome();
        MoneyAmount expenses = calculateExpenses();
        MoneyAmount profit = income.subtract(expenses);

        return new ProfitReport(income, expenses, profit);
    }

    private MoneyAmount calculateIncome() {
        MoneyAmount totalPrice = new MoneyAmount(0);
        for (Order order : this.orders) {
            MoneyAmount orderPrice = order.calculateOrderPrice();
            totalPrice = totalPrice.add(orderPrice);
        }
        return totalPrice;
    }

    private MoneyAmount calculateExpenses() {
        ShuttleManifest shuttleManifest = new ShuttleManifest(generateShuttleManifest(null));
        MoneyAmount transportExpenses = shuttleManifest.calculateTotalTransportsCost();

        OxygenReport oxygenReport = getOxygenReport();
        MoneyAmount oxygenExpenses = oxygenReport.calculateOxygenCost();

        ScheduleSimulation scheduleSimulation = new ScheduleSimulation(this.confirmedProgram);
        MoneyAmount artistExpenses = scheduleSimulation.getTotalCostForArtists();

        return transportExpenses.add(oxygenExpenses).add(artistExpenses);
    }

    public void setConfirmedProgram(Map<LocalDate, Artist> confirmedProgram) {
        this.confirmedProgram.putAll(confirmedProgram);
    }

    public MoneyAmount getArtistsTotalCost() {
        return artistsTotalCost;
    }

    public void setArtistsTotalCost(MoneyAmount artistsTotalCost) {
        this.artistsTotalCost = artistsTotalCost;
    }

    public boolean isProgramConfirmed() {
        return programIsConfirmed;
    }

    public FestivalDates getFestivalDates() {
        return festivalDates;
    }

    public LocalDateTime getProgramConfirmationDate() {
        return programConfirmationDate;
    }

    public void setProgramConfirmationDate(LocalDateTime programConfirmationDate) {
        this.programConfirmationDate = programConfirmationDate;
    }

    public void setProgramIsConfirmed(boolean programIsConfirmed) {
        this.programIsConfirmed = programIsConfirmed;
    }
}
