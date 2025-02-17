package ca.ulaval.glo4002.application.domain.order.price;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;

import java.util.Map;

public interface DiscountStrategy {
    MoneyAmount calculateDiscount(Map<PassCategoryTypes, Long> categoryCounts, MoneyAmount totalPrice);
}
