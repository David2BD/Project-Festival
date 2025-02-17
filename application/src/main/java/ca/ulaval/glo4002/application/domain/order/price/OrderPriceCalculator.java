package ca.ulaval.glo4002.application.domain.order.price;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderPriceCalculator {

    private final List<DiscountStrategy> discountStrategies;

    public OrderPriceCalculator() {
        this.discountStrategies = Arrays.asList(new PremiumDiscountStrategy(), new StandardDiscountStrategy());
    }

    public MoneyAmount calculateTotalPrice(List<Pass> passes) {
        MoneyAmount totalPrice = new MoneyAmount();

        for (Pass pass : passes) {
            MoneyAmount passPrice = new MoneyAmount(pass.getPassPrice());
            totalPrice = totalPrice.add(passPrice);
        }

        Map<PassCategoryTypes, Long> passesMappedByPassCategory = passes.stream().filter(Pass::canBeUsedForDiscount)
                .collect(Collectors.groupingBy(Pass::getPassCategory, Collectors.counting()));

        totalPrice = applyAllDiscounts(totalPrice, passesMappedByPassCategory);

        return totalPrice;
    }

    private MoneyAmount applyAllDiscounts(MoneyAmount totalPrice, Map<PassCategoryTypes, Long> categoryCounts) {
        for (DiscountStrategy strategy : discountStrategies) {
            totalPrice = totalPrice.subtract(strategy.calculateDiscount(categoryCounts, totalPrice));
        }
        return totalPrice;
    }
}