package ca.ulaval.glo4002.application.utils;

import ca.ulaval.glo4002.application.domain.MoneyAmount;

public class MoneyUtils {
    public static MoneyAmount parseMoneyAmount(String budget) {
        if (budget != null && ! budget.isBlank()) {
            try {
                return new MoneyAmount(Double.parseDouble(budget.trim()));
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid format for headliner budget: " + budget, e);
            }
        }
        return MoneyAmount.zero();
    }
}
