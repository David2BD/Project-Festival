package ca.ulaval.glo4002.application.domain.festival;

import ca.ulaval.glo4002.application.domain.pass.exceptions.InvalidEventDateException;
import ca.ulaval.glo4002.application.utils.DateUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FestivalDates {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public FestivalDates(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean validateDateIsInFestivalRange(LocalDate date) {
        return date != null && (date.isBefore(this.startDate) || date.isAfter(this.endDate));
    }

    public int getFestivalDurationInDays() {
        return ((int) ChronoUnit.DAYS.between(startDate, endDate) + 1);
    }

    public void verifyEventDate(LocalDate eventDate) {
        if (validateDateIsInFestivalRange(eventDate)) {
            throw new InvalidEventDateException(
                    "event date should be between " + DateUtils.formatLocalDateTimeToReadableDate(this.startDate) + " and " + DateUtils.formatLocalDateTimeToReadableDate(this.endDate));
        }
    }
}

