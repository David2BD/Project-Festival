package ca.ulaval.glo4002.application.domain.pass.categories;

import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;

public abstract class PassCategory {
    public abstract OxygenGrade getBaseOxygenGrade();

    public abstract PassCategoryTypes getPassCategory();

    public abstract int getOxygenDailyUse();

    public abstract ShuttleType getShuttleType();
}
