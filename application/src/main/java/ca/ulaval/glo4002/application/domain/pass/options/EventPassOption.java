package ca.ulaval.glo4002.application.domain.pass.options;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategory;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import ca.ulaval.glo4002.application.domain.pass.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.application.domain.transport.transportable.TransportableIndividual;
import ca.ulaval.glo4002.application.utils.DateUtils;

import java.time.LocalDate;
import java.util.List;

public class EventPassOption extends PassOption {
    private static final MoneyAmount EVENT_PASS_PRICE_STANDARD = new MoneyAmount(250000);
    private static final MoneyAmount EVENT_PASS_PRICE_PREMIUM = new MoneyAmount(500000);
    private static final MoneyAmount EVENT_PASS_PRICE_VIP = new MoneyAmount(700000);

    private LocalDate eventDate;

    public EventPassOption(LocalDate eventDate) {
        setEventDate(eventDate);
    }

    @Override
    public MoneyAmount getPassPrice(PassCategoryTypes passCategory) {
        return switch (passCategory) {
            case VIP -> EVENT_PASS_PRICE_VIP;
            case PREMIUM -> EVENT_PASS_PRICE_PREMIUM;
            case STANDARD -> EVENT_PASS_PRICE_STANDARD;
        };
    }

    @Override
    public PassOptionTypes getOptionType() {
        return PassOptionTypes.EVENT;
    }

    @Override
    public boolean canBeUsedForDiscount() {
        return false;
    }

    @Override
    public LocalDate getEventDate() {
        return eventDate;
    }

    @Override
    public List<LocalDate> getEventDates(LocalDate festivalStartDate, LocalDate festivalEndDate) {
        return DateUtils.generateDatesBetween(festivalStartDate, festivalEndDate);
    }

    @Override
    public int calculatePassDuration(FestivalDates festivalDates) {
        return festivalDates.getFestivalDurationInDays();
    }

    @Override
    public TransportableIndividual generateTransportable(
            PassCategory passCategory, PassNumber passNumber, LocalDate festivalStartDate, LocalDate festivalEndDate
    ) {
        return new TransportableIndividual(festivalStartDate, festivalEndDate, passCategory, passNumber);
    }

    private void setEventDate(LocalDate eventDate) {
        verifyEventDateIsNull(eventDate);
        this.eventDate = eventDate;
    }

    private void verifyEventDateIsNull(LocalDate eventDate) {
        if (eventDate != null) {
            throw new InvalidFormatException();
        }
    }
}
