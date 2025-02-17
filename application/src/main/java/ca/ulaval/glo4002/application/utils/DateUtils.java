package ca.ulaval.glo4002.application.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter ZONED_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    private DateUtils() {
    }

    public static String formatLocalDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }

    public static LocalDate parseToLocalDate(String date) {
        try {
            return date != null ? LocalDate.parse(date, DATE_FORMATTER) : null;
        }
        catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + date, e);
        }
    }

    public static String formatZonedDateTime(ZonedDateTime dateTime) {
        return dateTime != null ? dateTime.format(ZONED_DATE_TIME_FORMATTER) : null;
    }

    public static ZonedDateTime parseToZonedDateTime(String dateTime) {
        try {
            return dateTime != null ? ZonedDateTime.parse(dateTime, ZONED_DATE_TIME_FORMATTER) : null;
        }
        catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date-time format: " + dateTime, e);
        }
    }

    public static boolean isValidLocalDateOrNull(String dateStr) {
        if (dateStr == null) {
            return true;
        }
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        }
        catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidISODateTime(String dateTime) {
        try {
            ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
            return true;
        }
        catch (DateTimeParseException e) {
            return false;
        }
    }

    public static List<LocalDate> generateDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> eventDates = new ArrayList<>();
        LocalDate current = startDate;
        while (! current.isAfter(endDate)) {
            eventDates.add(current);
            current = current.plusDays(1);
        }
        return eventDates;
    }

    public static Date convertToDate(LocalDate localDate) {
        return (localDate != null) ? Date.valueOf(localDate) : null;
    }

    public static Timestamp convertToTimestamp(ZonedDateTime zonedDateTime) {
        return (zonedDateTime != null) ? Timestamp.from(zonedDateTime.toInstant()) : null;
    }

    public static LocalDate convertToLocalDate(Date date) {
        return (date != null) ? date.toLocalDate() : null;
    }

    public static ZonedDateTime convertToZonedDateTime(Timestamp timestamp) {
        return (timestamp != null) ? timestamp.toInstant().atZone(ZonedDateTime.now().getZone()) : null;
    }

    public static String formatZonedDateTimeToReadableDate(ZonedDateTime dateTime) {
        DateTimeFormatter readableFormatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
        return dateTime != null ? dateTime.format(readableFormatter) : null;
    }

    public static String formatLocalDateTimeToReadableDate(LocalDate date) {
        DateTimeFormatter readableFormatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
        return date != null ? date.format(readableFormatter) : null;
    }

    public static int numberOfDaysBetween(LocalDate start, LocalDate end) {
        return (int) ChronoUnit.DAYS.between(start, end);
    }

    public static LocalDateTime calculateTimestamp(LocalDateTime start, int intervalInDays) {
        return start.plusDays(intervalInDays);
    }

    public static LocalDateTime toLocalDateTime(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date to convert cannot be null");
        }
        return date.atStartOfDay();
    }

}
