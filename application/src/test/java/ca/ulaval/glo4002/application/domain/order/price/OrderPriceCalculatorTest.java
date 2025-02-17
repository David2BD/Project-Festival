package ca.ulaval.glo4002.application.domain.order.price;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class OrderPriceCalculatorTest {

    private OrderPriceCalculator priceCalculator;
    private List<Pass> passes;

    @Mock
    private final MoneyAmount EVENT_PASS_PRICE_STANDARD = new MoneyAmount(250000);

    @Mock
    private final MoneyAmount EVENT_PASS_PRICE_PREMIUM = new MoneyAmount(500000);

    @Mock
    private final MoneyAmount DAILY_PASS_PRICE_STANDARD = new MoneyAmount(50000);

    @Mock
    private final MoneyAmount DAILY_PASS_PRICE_PREMIUM = new MoneyAmount(100000);

    @Mock
    private final MoneyAmount DAILY_PASS_VOLUME_DISCOUNT_PREMIUM = new MoneyAmount(10000);

    @Mock
    private Pass standardDailyPass;

    @Mock
    private Pass premiumDailyPass;

    @Mock
    private Pass standardEventPass;

    @Mock
    private Pass premiumEventPass;

    @BeforeEach
    void setUp() {
        priceCalculator = new OrderPriceCalculator();
        passes = new ArrayList<>();

        lenient().when(EVENT_PASS_PRICE_STANDARD.getAmount()).thenReturn(BigDecimal.valueOf(250000));
        lenient().when(EVENT_PASS_PRICE_PREMIUM.getAmount()).thenReturn(BigDecimal.valueOf(500000));
        lenient().when(DAILY_PASS_PRICE_STANDARD.getAmount()).thenReturn(BigDecimal.valueOf(50000));
        lenient().when(DAILY_PASS_PRICE_PREMIUM.getAmount()).thenReturn(BigDecimal.valueOf(100000));
        lenient().when(DAILY_PASS_VOLUME_DISCOUNT_PREMIUM.getAmount()).thenReturn(BigDecimal.valueOf(10000));

        lenient().when(standardDailyPass.getPassCategory()).thenReturn(PassCategoryTypes.STANDARD);
        lenient().when(standardDailyPass.getPassPrice()).thenReturn(DAILY_PASS_PRICE_STANDARD);
        lenient().when(standardDailyPass.canBeUsedForDiscount()).thenReturn(true);

        lenient().when(premiumDailyPass.getPassCategory()).thenReturn(PassCategoryTypes.PREMIUM);
        lenient().when(premiumDailyPass.getPassPrice()).thenReturn(DAILY_PASS_PRICE_PREMIUM);
        lenient().when(premiumDailyPass.canBeUsedForDiscount()).thenReturn(true);

        lenient().when(standardEventPass.getPassCategory()).thenReturn(PassCategoryTypes.STANDARD);
        lenient().when(standardEventPass.getPassPrice()).thenReturn(EVENT_PASS_PRICE_STANDARD);
        lenient().when(standardEventPass.canBeUsedForDiscount()).thenReturn(false);

        lenient().when(premiumEventPass.getPassCategory()).thenReturn(PassCategoryTypes.PREMIUM);
        lenient().when(premiumEventPass.getPassPrice()).thenReturn(EVENT_PASS_PRICE_PREMIUM);
        lenient().when(premiumEventPass.canBeUsedForDiscount()).thenReturn(false);
    }

    private void addMockedPassesToPassesList(Pass pass, int count) {
        for (int i = 0; i < count; i++) {
            passes.add(pass);
        }
    }

    @Test
    void givenSingleStandardDailyPass_whenCalculateTotalPrice_thenNoDiscountIsApplied() {
        addMockedPassesToPassesList(standardDailyPass, 1);

        MoneyAmount totalPrice = priceCalculator.calculateTotalPrice(passes);
        MoneyAmount expectedPrice = DAILY_PASS_PRICE_STANDARD;

        assertEquals(expectedPrice.getAmount(), totalPrice.getAmount(),
                     "Total price should be $50000 without discounts.");
    }

    @Test
    void givenThreeStandardDailyPasses_whenCalculateTotalPrice_thenNoDiscountIsApplied() {
        addMockedPassesToPassesList(standardDailyPass, 3);

        MoneyAmount totalPrice = priceCalculator.calculateTotalPrice(passes);
        BigDecimal expectedPrice = BigDecimal.valueOf(150000);

        assertEquals(expectedPrice, totalPrice.getAmount(), "Total price should be $150000 without discounts.");
    }

    @Test
    void givenFourStandardDailyPasses_whenCalculateTotalPrice_then10PercentDiscountOffTotalIsApplied() {
        addMockedPassesToPassesList(standardDailyPass, 4);
        BigDecimal expectedPrice = BigDecimal.valueOf(180000.00).setScale(2);

        MoneyAmount totalPrice = priceCalculator.calculateTotalPrice(passes);

        assertEquals(expectedPrice, totalPrice.twoDecimals(),
                     "Total price should be $180000 after a 10% standard pass discount.");
    }

    @Test
    void givenHighVolumeOfStandardDailyPasses_whenCalculateTotalPrice_thenTotalPriceIsCorrectWithDiscounts() {
        addMockedPassesToPassesList(standardDailyPass, 100);
        BigDecimal expectedPrice = BigDecimal.valueOf(4500000).setScale(0);

        MoneyAmount totalPrice = priceCalculator.calculateTotalPrice(passes);

        assertEquals(expectedPrice, totalPrice.getAmount().setScale(0, RoundingMode.HALF_EVEN),
                     "Total price should be correctly calculated with high volume standard discount.");
    }

    @Test
    void givenFourPremiumDailyPasses_whenCalculateTotalPrice_thenNoDiscountIsApplied() {
        addMockedPassesToPassesList(premiumDailyPass, 4);

        MoneyAmount totalPrice = priceCalculator.calculateTotalPrice(passes);
        BigDecimal expectedPrice = BigDecimal.valueOf(400000).setScale(0);

        assertEquals(expectedPrice, totalPrice.getAmount().setScale(0, RoundingMode.HALF_EVEN),
                     "Total price should be $400000 without discounts.");
    }

    @Test
    void givenFivePremiumDailyPasses_whenCalculateTotalPrice_then10000DiscountPerDailyPremiumPassIsApplied() {
        addMockedPassesToPassesList(premiumDailyPass, 5);
        BigDecimal expectedPrice = BigDecimal.valueOf(450000).setScale(0);

        MoneyAmount totalPrice = priceCalculator.calculateTotalPrice(passes);

        assertEquals(expectedPrice, totalPrice.getAmount().setScale(0),
                     "Total price should have premium discount applied.");
    }

    @Test
    void givenFivePremiumAndFourStandardDailyPasses_whenCalculateTotalPrice_thenBothDiscountsAreApplied() {
        addMockedPassesToPassesList(premiumDailyPass, 5);
        addMockedPassesToPassesList(standardDailyPass, 4);
        BigDecimal expectedPrice = BigDecimal.valueOf(585000).setScale(0);

        MoneyAmount totalPrice = priceCalculator.calculateTotalPrice(passes);

        assertEquals(expectedPrice, totalPrice.getAmount().setScale(0, RoundingMode.HALF_EVEN),
                     "Both discounts should be applied correctly.");
    }

    @Test
    void givenEventPassesOnly_whenCalculateTotalPrice_thenNoDiscountIsApplied() {
        passes.add(standardEventPass);
        passes.add(premiumEventPass);
        BigDecimal expectedPrice = BigDecimal.valueOf(750000);

        MoneyAmount totalPrice = priceCalculator.calculateTotalPrice(passes);

        assertEquals(expectedPrice, totalPrice.getAmount().setScale(0, RoundingMode.HALF_EVEN),
                     "Total price should be 750000 without discounts.");
    }

    @Test
    void givenMixedPassTypesWithDiscounts_whenCalculateTotalPrice_thenCorrectTotalPriceIsCalculated() {
        addMockedPassesToPassesList(standardDailyPass, 4);
        addMockedPassesToPassesList(premiumDailyPass, 5);
        passes.add(standardEventPass);
        BigDecimal expectedPrice = BigDecimal.valueOf(810000).setScale(0);

        MoneyAmount totalPrice = priceCalculator.calculateTotalPrice(passes);

        assertEquals(expectedPrice, totalPrice.getAmount().setScale(0, RoundingMode.HALF_EVEN),
                     "Correct total price with both discounts should be calculated.");
    }

    @Test
    void givenMixedEventPassesWithoutEligibleDiscounts_whenCalculateTotalPrice_thenNoDiscountIsApplied() {
        passes.add(standardEventPass);
        passes.add(premiumEventPass);
        passes.add(standardEventPass);
        BigDecimal expectedPrice = BigDecimal.valueOf(1000000).setScale(0);

        MoneyAmount totalPrice = priceCalculator.calculateTotalPrice(passes);

        assertEquals(expectedPrice, totalPrice.getAmount().setScale(0, RoundingMode.HALF_EVEN),
                     "Total price should be correct without discounts for event passes.");
    }

}
