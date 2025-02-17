package ca.ulaval.glo4002.application.domain.pass;

import ca.ulaval.glo4002.application.domain.pass.categories.PassCategory;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import ca.ulaval.glo4002.application.domain.pass.categories.PremiumPassCategory;
import ca.ulaval.glo4002.application.domain.pass.categories.StandardPassCategory;
import ca.ulaval.glo4002.application.domain.pass.categories.VipPassCategory;
import ca.ulaval.glo4002.application.domain.pass.options.DailyPassOption;
import ca.ulaval.glo4002.application.domain.pass.options.EventPassOption;
import ca.ulaval.glo4002.application.domain.pass.options.PassOption;
import ca.ulaval.glo4002.application.domain.pass.options.PassOptionTypes;
import ca.ulaval.glo4002.application.utils.DateUtils;

import java.time.LocalDate;

public class PassFactory {

    public Pass createPass(
            String passCategory, String passOption, String eventDate
    ) {
        LocalDate parsedEventDate = DateUtils.parseToLocalDate(eventDate);

        PassCategory category = createPassCategory(passCategory);
        PassOption option = createPassOption(passOption, parsedEventDate);

        return new Pass(category, option);
    }

    public Pass createPassWithNumber(String passCategory, String passOption, String eventDate, PassNumber passNumber) {
        LocalDate parsedEventDate = DateUtils.parseToLocalDate(eventDate);

        PassCategory category = createPassCategory(passCategory);
        PassOption option = createPassOption(passOption, parsedEventDate);

        return new Pass(passNumber, category, option);
    }

    private PassCategory createPassCategory(String passCategory) {
        return switch (PassCategoryTypes.fromString(passCategory)) {
            case VIP -> new VipPassCategory();
            case PREMIUM -> new PremiumPassCategory();
            case STANDARD -> new StandardPassCategory();
        };
    }

    private PassOption createPassOption(
            String passOption, LocalDate parsedEventDate
    ) {
        return switch (PassOptionTypes.fromString(passOption)) {
            case DAILY -> new DailyPassOption(parsedEventDate);
            case EVENT -> new EventPassOption(parsedEventDate);
        };
    }
}
