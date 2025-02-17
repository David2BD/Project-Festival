package ca.ulaval.glo4002.application.domain.pass.options;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategory;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import ca.ulaval.glo4002.application.domain.pass.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.application.domain.transport.transportable.TransportableIndividual;

import java.time.LocalDate;
import java.util.List;

public class DailyPassOption extends PassOption {
    private static final MoneyAmount DAILY_PASS_PRICE_STANDARD = new MoneyAmount(50000);
    private static final MoneyAmount DAILY_PASS_PRICE_PREMIUM = new MoneyAmount(100000);
    private static final MoneyAmount DAILY_PASS_PRICE_VIP = new MoneyAmount(150000);
    private static final int PASS_DURATION_IN_DAYS = 1;
    private LocalDate eventDate;

    public DailyPassOption(LocalDate eventDate) {
        setEventDate(eventDate);
    }

    @Override
    public boolean canBeUsedForDiscount() {
        return true;
    }

    @Override
    public List<LocalDate> getEventDates(LocalDate festivalStartDate, LocalDate festivalEndDate) {
        return List.of(this.eventDate);
    }

    @Override
    public PassOptionTypes getOptionType() {
        return PassOptionTypes.DAILY;
    }

    @Override
    public LocalDate getEventDate() {
        return eventDate;
    }

    @Override
    public MoneyAmount getPassPrice(PassCategoryTypes passCategory) {
        return switch (passCategory) {
            case VIP -> DAILY_PASS_PRICE_VIP;
            case PREMIUM -> DAILY_PASS_PRICE_PREMIUM;
            case STANDARD -> DAILY_PASS_PRICE_STANDARD;
        };
    }

    @Override
    public int calculatePassDuration(FestivalDates festivalDates) {
        return PASS_DURATION_IN_DAYS;
    }

    @Override
    public TransportableIndividual generateTransportable(
            PassCategory passCategory, PassNumber passNumber, LocalDate festivalStartDate, LocalDate festivalEndDate
    ) {
        return new TransportableIndividual(eventDate, eventDate, passCategory, passNumber);
    }

    private void setEventDate(LocalDate eventDate) {
        verifyEventDateIsNotNull(eventDate);

        this.eventDate = eventDate;
    }

    private void verifyEventDateIsNotNull(LocalDate eventDate) {
        if (eventDate == null) {
            throw new InvalidFormatException("invalid format");
        }
    }
}
