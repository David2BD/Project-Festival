package ca.ulaval.glo4002.application.domain.order.price;

import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import ca.ulaval.glo4002.application.domain.MoneyAmount;

import java.util.Map;

public class PremiumDiscountStrategy implements DiscountStrategy {
    private static final MoneyAmount PREMIUM_PASS_VOLUME_DISCOUNT = new MoneyAmount(10000);

    @Override
    public MoneyAmount calculateDiscount(Map<PassCategoryTypes, Long> categoryCounts, MoneyAmount totalPrice) {
        Long nbPassesPremium = categoryCounts.getOrDefault(PassCategoryTypes.PREMIUM, 0L);
        if (nbPassesPremium != null && nbPassesPremium >= 5) {
            return PREMIUM_PASS_VOLUME_DISCOUNT.multiply(nbPassesPremium);
        }
        return new MoneyAmount();
    }
}
