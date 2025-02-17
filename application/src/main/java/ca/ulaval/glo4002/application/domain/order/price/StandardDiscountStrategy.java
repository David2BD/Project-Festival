package ca.ulaval.glo4002.application.domain.order.price;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;

import java.util.Map;

public class StandardDiscountStrategy implements DiscountStrategy {
    private static final float STANDARD_PASS_VOLUME_DISCOUNT_RATE = 0.1f;

    @Override
    public MoneyAmount calculateDiscount(Map<PassCategoryTypes, Long> categoryCounts, MoneyAmount totalPrice) {
        Long nbPassesStandard = categoryCounts.get(PassCategoryTypes.STANDARD);
        if (nbPassesStandard != null && nbPassesStandard > 3) {
            return totalPrice.multiply(STANDARD_PASS_VOLUME_DISCOUNT_RATE);
        }
        return new MoneyAmount();
    }
}
