package ca.ulaval.glo4002.application.domain.pass.categories;

import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;

public class VipPassCategory extends PassCategory {
    private static final int DAILY_OXYGEN_USE_VIP = 5;

    @Override
    public OxygenGrade getBaseOxygenGrade() {
        return OxygenGrade.E;
    }

    @Override
    public PassCategoryTypes getPassCategory() {
        return PassCategoryTypes.VIP;
    }

    @Override
    public int getOxygenDailyUse() {
        return DAILY_OXYGEN_USE_VIP;
    }

    @Override
    public ShuttleType getShuttleType() {
        return ShuttleType.ET_SPACESHIP;
    }
}
