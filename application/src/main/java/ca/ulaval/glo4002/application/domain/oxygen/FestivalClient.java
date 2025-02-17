package ca.ulaval.glo4002.application.domain.oxygen;

import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategory;
import ca.ulaval.glo4002.application.domain.pass.options.PassOption;

public class FestivalClient extends OxygenRequester {

    private final PassCategory category;
    private final PassOption option;

    public FestivalClient(PassCategory category, PassOption option) {
        this.category = category;
        this.option = option;
    }

    @Override
    public OxygenGrade getBaseOxygenGrade() {
        return category.getBaseOxygenGrade();
    }

    @Override
    public int calculateTanksNeeded(FestivalDates festivalDates) {
        return option.calculatePassDuration(festivalDates) * category.getOxygenDailyUse();
    }
}
