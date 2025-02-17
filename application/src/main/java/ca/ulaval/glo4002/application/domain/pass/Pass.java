package ca.ulaval.glo4002.application.domain.pass;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.oxygen.FestivalClient;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategory;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import ca.ulaval.glo4002.application.domain.pass.options.PassOption;
import ca.ulaval.glo4002.application.domain.pass.options.PassOptionTypes;
import ca.ulaval.glo4002.application.domain.transport.transportable.TransportableIndividual;

import java.time.LocalDate;
import java.util.List;

public class Pass {
    private final PassNumber passNumber;
    private final PassCategory category;
    private final PassOption option;

    public Pass(PassCategory category, PassOption option) {
        this.passNumber = PassNumber.generateNewPassNumber();
        this.category = category;
        this.option = option;
    }

    public FestivalClient createFestivalClient() {
        return new FestivalClient(this.category, this.option);
    }

    public Pass(PassNumber passNumber, PassCategory category, PassOption option) {
        this.passNumber = passNumber;
        this.category = category;
        this.option = option;
    }

    public PassCategoryTypes getPassCategory() {
        return category.getPassCategory();
    }

    public PassNumber getPassNumber() {
        return passNumber;
    }

    public LocalDate getEventDate() {
        return option.getEventDate();
    }

    public MoneyAmount getPassPrice() {
        return option.getPassPrice(category.getPassCategory());
    }

    public boolean canBeUsedForDiscount() {
        return option.canBeUsedForDiscount();
    }

    public List<LocalDate> getEventDates(LocalDate festivalStartDate, LocalDate festivalEndDate) {
        return option.getEventDates(festivalStartDate, festivalEndDate);
    }

    public String getPassNumberValue() {
        return passNumber.getNumber();
    }

    public PassOptionTypes getPassOption() {
        return option.getOptionType();
    }

    public TransportableIndividual generateTransportable(
            LocalDate festivalStartDate, LocalDate festivalEndDate
    ) {
        return option.generateTransportable(category, passNumber, festivalStartDate, festivalEndDate);
    }
}
