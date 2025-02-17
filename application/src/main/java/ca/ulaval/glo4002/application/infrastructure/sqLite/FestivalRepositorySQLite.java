package ca.ulaval.glo4002.application.infrastructure.sqLite;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import ca.ulaval.glo4002.application.domain.order.Order;
import ca.ulaval.glo4002.application.domain.order.OrderDates;
import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.ConfirmedProgram;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;
import ca.ulaval.glo4002.application.infrastructure.exceptions.OrdersNotFoundException;
import ca.ulaval.glo4002.application.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FestivalRepositorySQLite implements FestivalRepository {
    private static final Logger logger = LoggerFactory.getLogger(FestivalRepositorySQLite.class);

    private static final String INSERT_ORDER_SQL =
            "INSERT INTO orders(order_number, order_date, vendor_code,  fk_festival_id) VALUES(?, ?, ?, ?)";

    private static final String SELECT_ALL_ORDERS_SQL = "SELECT * FROM orders WHERE fk_festival_id = ?";
    private static final String SELECT_ORDER_BY_NUMBER_SQL = "SELECT order_number FROM orders WHERE order_number = ?";

    private static final String SELECT_PASSES_SQL = "SELECT * FROM passes WHERE fk_order_number = ?";
    private static final String INSERT_PASS_SQL =
            "INSERT INTO passes(fk_order_number, pass_option, pass_category, event_date, pass_number) VALUES(?, ?, ?, ?, ?)";
    private static final String SELECT_PASS_BY_NUMBER_SQL = "SELECT pass_number FROM passes WHERE pass_number = ?";

    private static final String SELECT_ALL_FESTIVAL_SQL = "SELECT * FROM festival";
    private static final String SELECT_FESTIVAL_BY_ID_SQL = "SELECT festival_id FROM festival WHERE festival_id = ?";
    private static final String INSERT_FESTIVAL_SQL =
            "INSERT INTO festival(festival_id, festival_name, festival_start_date, festival_end_date, orders_opening_date, orders_closing_date, festival_vendor_code) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_CONFIRMED_PROGRAM_SQL =
            "SELECT program_date, artist_name, artist_cost, popularity, group_people_count, musical_genre, shuttle_type " +
            "FROM confirmed_program WHERE festival_id = ?";

    private static final String SELECT_PROGRAM_DETAILS_SQL =
            "SELECT artists_total_cost, program_is_confirmed, confirmation_date FROM program_details WHERE festival_id = ?";

    private static final String INSERT_PROGRAM_DETAILS_SQL =
            "INSERT INTO program_details (festival_id, artists_total_cost, program_is_confirmed, confirmation_date) " +
            "VALUES (?, ?, ?, ?) " +
            "ON CONFLICT(festival_id) DO UPDATE SET " +
            "artists_total_cost = excluded.artists_total_cost, " +
            "program_is_confirmed = excluded.program_is_confirmed, " +
            "confirmation_date = excluded.confirmation_date";

    private static final String INSERT_CONFIRMED_PROGRAM_SQL =
            "INSERT INTO confirmed_program (festival_id, program_date, artist_name, artist_cost, popularity, group_people_count, musical_genre, shuttle_type) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private final Connection connection;
    private final OrderMapperSQLite orderMapperSQLite;
    private final PassMapperSQLite passMapperSQLite = new PassMapperSQLite();

    public FestivalRepositorySQLite(Connection connection, OrderMapperSQLite orderMapperSQLite) {
        this.connection = connection;
        this.orderMapperSQLite = orderMapperSQLite;
    }

    @Override
    public void saveFestival(Festival festival) {
        logger.info("Saving festival: " + festival.getId());
        saveFestivalToDatabase(festival);
    }

    @Override
    public void addFestival(Festival festival) {
        if (! festivalExists(festival.getId())) {
            insertFestivalToDatabase(festival);
        }
        else {
            logger.info("Festival with ID " + festival.getId() + " already exists. Skipping addition.");
        }
    }

    @Override
    public Festival getFestival() {
        Festival festival = null;
        try (PreparedStatement pstmt = connection.prepareStatement(SELECT_ALL_FESTIVAL_SQL)) {
            ResultSet rsFestival = pstmt.executeQuery();
            if (rsFestival.next()) {
                festival = extractFestivalFromResultSet(rsFestival);
                festival.setConfirmedProgram(loadConfirmedProgram(festival.getId()));
                loadProgramDetails(festival);

                List<Order> orders = listAllOrders(festival.getId());
                for (Order order : orders) {
                    festival.addOrder(order);
                }
            }
        }
        catch (SQLException e) {
            logger.error("Error retrieving festival: " + e.getMessage());
        }
        return festival;
    }

    private void saveFestivalToDatabase(Festival festival) {
        if (! festivalExists(festival.getId())) {
            insertFestivalToDatabase(festival);
        }
        if (festival.isProgramConfirmed()) {
            saveProgramDetails(festival);
            saveConfirmedProgram(festival);
        }

        for (Order order : festival.getOrders()) {
            try {
                saveOrderToDatabase(order, festival.getId());
                savePassesToDatabase(order);
            }
            catch (SQLException e) {
                logger.error("Error saving orders and passes for festival: " + e.getMessage());
            }
        }
    }

    private boolean festivalExists(String festivalId) {
        try (PreparedStatement pstmt = connection.prepareStatement(SELECT_FESTIVAL_BY_ID_SQL)) {
            pstmt.setString(1, festivalId);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
        catch (SQLException e) {
            logger.error("Error checking if festival exists: " + e.getMessage());
            return false;
        }
    }

    private void insertFestivalToDatabase(Festival festival) {
        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_FESTIVAL_SQL)) {
            pstmt.setString(1, festival.getId());
            pstmt.setString(2, festival.getName());
            pstmt.setDate(3, DateUtils.convertToDate(festival.getFestivalStartDate()));
            pstmt.setDate(4, DateUtils.convertToDate(festival.getFestivalEndDate()));
            pstmt.setTimestamp(5, DateUtils.convertToTimestamp(festival.getOrdersOpeningDate()));
            pstmt.setTimestamp(6, DateUtils.convertToTimestamp(festival.getOrdersClosingDate()));
            pstmt.setString(7, festival.getVendorCode());
            pstmt.executeUpdate();
            logger.info("Festival inserted with ID: " + festival.getId());
        }
        catch (SQLException e) {
            logger.error("Error inserting festival: " + e.getMessage());
        }
    }

    private Festival extractFestivalFromResultSet(ResultSet rsFestival) throws SQLException {
        String festivalId = rsFestival.getString("festival_id");
        String festivalName = rsFestival.getString("festival_name");
        Date festivalStartDate = rsFestival.getDate("festival_start_date");
        Date festivalEndDate = rsFestival.getDate("festival_end_date");
        Timestamp ordersOpeningDate = rsFestival.getTimestamp("orders_opening_date");
        Timestamp ordersClosingDate = rsFestival.getTimestamp("orders_closing_date");
        String vendorCode = rsFestival.getString("festival_vendor_code");
        FestivalDates festivalDates = extractFestivalDateFromResultSet(festivalStartDate, festivalEndDate);
        OrderDates orderDates = extractOrderDatesFromResultSet(ordersOpeningDate, ordersClosingDate);

        return new Festival(festivalId, festivalName, festivalDates, orderDates, vendorCode);
    }

    private FestivalDates extractFestivalDateFromResultSet(Date festivalStartDate, Date festivalEndDate) {
        return new FestivalDates(DateUtils.convertToLocalDate(festivalStartDate),
                                 DateUtils.convertToLocalDate(festivalEndDate));
    }

    private OrderDates extractOrderDatesFromResultSet(Timestamp ordersOpeningDate, Timestamp ordersClosingDate) {
        return new OrderDates(DateUtils.convertToZonedDateTime(ordersOpeningDate),
                              DateUtils.convertToZonedDateTime(ordersClosingDate));
    }

    private Order extractOrderFromResultSet(ResultSet rsOrder, Long orderNumber) throws SQLException {
        String vendorCode = rsOrder.getString("vendor_Code");
        String orderDate = rsOrder.getString("order_Date");

        OrderSQLite orderSQLite = new OrderSQLite(orderNumber, orderDate, vendorCode);
        List<Pass> passes = getPassesForOrder(orderNumber);
        return orderMapperSQLite.convertFromSQLite(orderSQLite, passes);
    }

    private void saveOrderToDatabase(Order order, String festivalID) throws SQLException {
        if (! orderExists(order.getOrderNumber())) {
            try (PreparedStatement pstmtOrder = connection.prepareStatement(INSERT_ORDER_SQL)) {
                OrderSQLite orderSQLite = orderMapperSQLite.convertToSQLite(order);
                setOrderStatementParameters(pstmtOrder, orderSQLite);
                pstmtOrder.setString(4, festivalID);
                pstmtOrder.executeUpdate();
                logger.info("Order saved with number: " + order.getOrderNumber());
            }
            catch (SQLException e) {
                logger.error("Error saving order with number " + order.getOrderNumber() + ": " + e.getMessage());
                throw e;
            }
        }
    }

    private boolean orderExists(Long orderNumber) {
        try (PreparedStatement pstmt = connection.prepareStatement(SELECT_ORDER_BY_NUMBER_SQL)) {
            pstmt.setLong(1, orderNumber);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
        catch (SQLException e) {
            logger.error("Error checking if order exists: " + e.getMessage());
            return false;
        }
    }

    private boolean passExists(PassNumber passNumber) {
        try (PreparedStatement pstmt = connection.prepareStatement(SELECT_PASS_BY_NUMBER_SQL)) {
            pstmt.setString(1, passNumber.getNumber());
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
        catch (SQLException e) {
            logger.error("Error checking if pass exists: " + e.getMessage());
            return false;
        }
    }

    private void setOrderStatementParameters(
            PreparedStatement pstmtOrder, OrderSQLite orderSQLite
    ) throws SQLException {
        pstmtOrder.setLong(1, orderSQLite.orderNumber());
        pstmtOrder.setString(2, orderSQLite.orderDate());
        pstmtOrder.setString(3, orderSQLite.vendorCode());
    }

    private void savePassesToDatabase(Order order) throws SQLException {
        for (Pass pass : order.getPasses()) {
            savePassToDatabase(order.getOrderNumber(), pass);
        }
    }

    private void savePassToDatabase(Long orderNumber, Pass pass) throws SQLException {
        if (! passExists(pass.getPassNumber())) {
            try (PreparedStatement pstmtPass = connection.prepareStatement(INSERT_PASS_SQL)) {
                PassSQLite passSQLite = passMapperSQLite.convertToSQLite(pass);
                setPassStatementParameters(pstmtPass, orderNumber, passSQLite);
                pstmtPass.executeUpdate();
            }
            catch (SQLException e) {
                logger.error("Error saving pass with number " + pass.getPassNumber() + ": " + e.getMessage());
                throw e;
            }
        }
    }

    private void setPassStatementParameters(
            PreparedStatement pstmtPass, Long orderNumber, PassSQLite passSQLite
    ) throws SQLException {
        pstmtPass.setLong(1, orderNumber);
        pstmtPass.setString(2, passSQLite.passOption());
        pstmtPass.setString(3, passSQLite.passCategory());
        pstmtPass.setString(5, passSQLite.passNumber());
        if (passSQLite.eventDate() != null) {
            pstmtPass.setString(4, passSQLite.eventDate());
        }
        else {
            pstmtPass.setNull(4, Types.VARCHAR);
        }
    }

    private List<Order> listAllOrders(String festivalId) throws OrdersNotFoundException {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(SELECT_ALL_ORDERS_SQL)) {
            pstmt.setString(1, festivalId);
            ResultSet rsOrders = pstmt.executeQuery();

            while (rsOrders.next()) {
                long orderNumber = rsOrders.getLong("order_number");
                Order order = extractOrderFromResultSet(rsOrders, orderNumber);
                orders.add(order);
            }

        }
        catch (SQLException e) {
            throw new OrdersNotFoundException("Error while retrieving orders: " + e.getMessage(), e);
        }

        return orders;
    }

    private List<Pass> getPassesForOrder(Long orderNumber) throws SQLException {
        List<Pass> passes = new ArrayList<>();

        try (PreparedStatement pstmtPasses = connection.prepareStatement(SELECT_PASSES_SQL)) {
            pstmtPasses.setString(1, orderNumber.toString());
            ResultSet rsPasses = pstmtPasses.executeQuery();
            while (rsPasses.next()) {
                passes.add(extractPassFromResultSet(rsPasses));
            }
        }
        catch (SQLException e) {
            throw new SQLException("Error retrieving passes: " + e.getMessage());
        }

        return passes;
    }

    private Pass extractPassFromResultSet(ResultSet rsPasses) throws SQLException {
        String passOption = rsPasses.getString("pass_Option");
        String passCategory = rsPasses.getString("pass_Category");
        String eventDate = rsPasses.getString("event_Date");
        String passNumber = rsPasses.getString("pass_number");

        PassSQLite passSQLite = new PassSQLite(passOption, passCategory, eventDate, passNumber);
        return passMapperSQLite.convertFromSQLite(passSQLite);
    }

    private void saveProgramDetails(Festival festival) {
        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_PROGRAM_DETAILS_SQL)) {
            pstmt.setString(1, festival.getId());
            pstmt.setBigDecimal(2, festival.getArtistsTotalCost().getAmount());
            pstmt.setBoolean(3, festival.isProgramConfirmed());

            LocalDate confirmationDate =
                    festival.getProgramConfirmationDate() != null ? festival.getProgramConfirmationDate().toLocalDate()
                            : null;

            pstmt.setDate(4, DateUtils.convertToDate(confirmationDate));
            pstmt.executeUpdate();
            logger.info("Program details saved for festival: " + festival.getId());
        }
        catch (SQLException e) {
            logger.error("Error saving program details for festival: " + e.getMessage());
        }
    }

    private void saveConfirmedProgram(Festival festival) {
        ConfirmedProgram confirmedProgram = festival.getConfirmedProgram();
        Map<LocalDate, Artist> schedule = confirmedProgram.getSchedule();

        for (Map.Entry<LocalDate, Artist> entry : schedule.entrySet()) {
            LocalDate date = entry.getKey();
            Artist artist = entry.getValue();

            try (PreparedStatement pstmt = connection.prepareStatement(INSERT_CONFIRMED_PROGRAM_SQL)) {
                pstmt.setString(1, festival.getId());
                pstmt.setDate(2, DateUtils.convertToDate(date));
                pstmt.setString(3, artist.getName());
                pstmt.setBigDecimal(4, artist.getCostValue());
                pstmt.setInt(5, artist.getPopularity());
                pstmt.setInt(6, artist.getGroupPeopleCount());
                pstmt.setString(7, artist.getMusicalGenre());
                pstmt.setString(8, artist.getShuttleType().name());
                pstmt.executeUpdate();
            }
            catch (SQLException e) {
                logger.error("Error saving confirmed program for date " +
                             date +
                             " and artist " +
                             artist.getName() +
                             ": " +
                             e.getMessage());
            }
        }
    }

    private Map<LocalDate, Artist> loadConfirmedProgram(String festivalId) throws SQLException {
        Map<LocalDate, Artist> confirmedProgram = new HashMap<>();
        try (PreparedStatement pstmt = connection.prepareStatement(SELECT_CONFIRMED_PROGRAM_SQL)) {
            pstmt.setString(1, festivalId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                LocalDate date = rs.getDate("program_date").toLocalDate();
                String artistName = rs.getString("artist_name");
                BigDecimal artistCost = rs.getBigDecimal("artist_cost");
                int popularity = rs.getInt("popularity");
                int groupPeopleCount = rs.getInt("group_people_count");
                String musicalGenre = rs.getString("musical_genre");
                ShuttleType shuttleType = ShuttleType.valueOf(rs.getString("shuttle_type"));

                Artist artist =
                        new Artist(artistName, new MoneyAmount(artistCost), popularity, groupPeopleCount, musicalGenre);
                artist.setShuttleType(shuttleType);
                confirmedProgram.put(date, artist);
            }
        }
        return confirmedProgram;
    }

    private void loadProgramDetails(Festival festival) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(SELECT_PROGRAM_DETAILS_SQL)) {
            pstmt.setString(1, festival.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                festival.setArtistsTotalCost(new MoneyAmount(rs.getBigDecimal("artists_total_cost")));
                festival.setProgramIsConfirmed(rs.getBoolean("program_is_confirmed"));
                if (rs.getDate("confirmation_date") != null) {
                    festival.setProgramConfirmationDate(rs.getDate("confirmation_date").toLocalDate().atStartOfDay());
                }
            }
        }

        Map<LocalDate, Artist> confirmedProgram = loadConfirmedProgram(festival.getId());
        festival.setConfirmedProgram(confirmedProgram);
    }

}
