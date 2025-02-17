package ca.ulaval.glo4002.application.domain.scheduleSimulation;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ScheduleSimulationTest {

    @Mock
    private Artist artist1Mock;
    @Mock
    private Artist artist2Mock;
    @Mock
    private ShuttleType shuttleType1Mock;
    @Mock
    private ShuttleType shuttleType2Mock;

    private final MoneyAmount artist1Cost = new MoneyAmount(100);
    private final MoneyAmount artist2Cost = new MoneyAmount(200);
    private final MoneyAmount shuttlePrice1 = new MoneyAmount(50);
    private final MoneyAmount shuttlePrice2 = new MoneyAmount(80);
    private final LocalDate date1 = LocalDate.of(2024, 11, 1);
    private final LocalDate date2 = LocalDate.of(2024, 11, 2);

    private Map<LocalDate, Artist> schedule;
    private Map<LocalDate, Artist> emptySchedule;

    @BeforeEach
    void setUp() {
        lenient().when(artist1Mock.getCost()).thenReturn(artist1Cost);
        lenient().when(artist2Mock.getCost()).thenReturn(artist2Cost);
        lenient().when(artist1Mock.getShuttleType()).thenReturn(shuttleType1Mock);
        lenient().when(shuttleType1Mock.getPrice()).thenReturn(shuttlePrice1);
        lenient().when(artist2Mock.getShuttleType()).thenReturn(shuttleType2Mock);
        lenient().when(shuttleType2Mock.getPrice()).thenReturn(shuttlePrice2);

        schedule = new HashMap<>();
        schedule.put(date1, artist1Mock);
        schedule.put(date2, artist2Mock);

        emptySchedule = new HashMap<>();
    }

    @Test
    void givenScheduleWithArtists_whenCalculateTotalCost_thenCostForArtistIsSumOfArtistCosts() {
        ScheduleSimulation scheduleSimulation = new ScheduleSimulation(schedule);

        MoneyAmount totalCost = scheduleSimulation.getTotalCostForArtists();

        MoneyAmount expectedTotalCost = artist1Cost.add(artist2Cost);
        assertEquals(expectedTotalCost.getAmount(), totalCost.getAmount());
    }

    @Test
    void givenEmptySchedule_whenCalculateTotalCost_thenCostForArtistIsZero() {
        ScheduleSimulation scheduleSimulation = new ScheduleSimulation(emptySchedule);

        MoneyAmount totalCost = scheduleSimulation.getTotalCostForArtists();

        MoneyAmount expectedTotalCost = new MoneyAmount(0);
        assertEquals(expectedTotalCost.getAmount(), totalCost.getAmount());
    }

    @Test
    void givenScheduleWithArtists_whenCalculateTransportCost_thenCostForTransportsIsSumOfShuttlePricesMultipliedByTwo() {
        ScheduleSimulation scheduleSimulation = new ScheduleSimulation(schedule);

        MoneyAmount transportCost = scheduleSimulation.calculateCostForArtistsTransports();

        MoneyAmount expectedTransportCost = shuttlePrice1.multiply(2).add(shuttlePrice2.multiply(2));
        assertEquals(expectedTransportCost.getAmount(), transportCost.getAmount());
    }

    @Test
    void givenEmptySchedule_whenCalculateTransportCost_thenCostForTransportsIsZero() {
        ScheduleSimulation scheduleSimulation = new ScheduleSimulation(emptySchedule);

        MoneyAmount transportCost = scheduleSimulation.calculateCostForArtistsTransports();

        MoneyAmount expectedTransportCost = new MoneyAmount(0);
        assertEquals(expectedTransportCost.getAmount(), transportCost.getAmount());
    }
}
