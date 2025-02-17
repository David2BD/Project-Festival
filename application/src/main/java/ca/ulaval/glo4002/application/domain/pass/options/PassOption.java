package ca.ulaval.glo4002.application.domain.pass.options;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategory;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import ca.ulaval.glo4002.application.domain.transport.transportable.TransportableIndividual;

import java.time.LocalDate;
import java.util.List;

public abstract class PassOption {
    public abstract boolean canBeUsedForDiscount();

    public abstract List<LocalDate> getEventDates(LocalDate festivalStartDate, LocalDate festivalEndDate);

    public abstract PassOptionTypes getOptionType();

    public abstract LocalDate getEventDate();

    public abstract MoneyAmount getPassPrice(PassCategoryTypes category);

    public abstract int calculatePassDuration(FestivalDates festivalDates);

    public abstract TransportableIndividual generateTransportable(
            PassCategory passCategory, PassNumber passNumber, LocalDate festivalStartDate, LocalDate festivalEndDate
    );
}
