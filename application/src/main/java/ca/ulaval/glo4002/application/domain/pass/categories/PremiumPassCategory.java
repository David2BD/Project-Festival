package ca.ulaval.glo4002.application.domain.pass.categories;

import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;

public class PremiumPassCategory extends PassCategory {
    private static final int DAILY_OXYGEN_USE_PREMIUM = 3;

    @Override
    public OxygenGrade getBaseOxygenGrade() {
        return OxygenGrade.B;
    }

    @Override
    public PassCategoryTypes getPassCategory() {
        return PassCategoryTypes.PREMIUM;
    }

    @Override
    public int getOxygenDailyUse() {
        return DAILY_OXYGEN_USE_PREMIUM;
    }

    @Override
    public ShuttleType getShuttleType() {
        return ShuttleType.MILLENNIUM_FALCON;
    }
}
