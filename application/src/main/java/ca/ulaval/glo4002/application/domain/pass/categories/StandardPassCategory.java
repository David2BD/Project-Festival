package ca.ulaval.glo4002.application.domain.pass.categories;

import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;

public class StandardPassCategory extends PassCategory {
    private static final int DAILY_OXYGEN_USE_STANDARD = 3;

    @Override
    public OxygenGrade getBaseOxygenGrade() {
        return OxygenGrade.A;
    }

    @Override
    public PassCategoryTypes getPassCategory() {
        return PassCategoryTypes.STANDARD;
    }

    @Override
    public int getOxygenDailyUse() {
        return DAILY_OXYGEN_USE_STANDARD;
    }

    @Override
    public ShuttleType getShuttleType() {
        return ShuttleType.SPACE_X;
    }
}
