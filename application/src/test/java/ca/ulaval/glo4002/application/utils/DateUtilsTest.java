package ca.ulaval.glo4002.application.utils;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {
    private static final String LOCAL_DATE_STRING = "2060-05-21";
    private static final LocalDate LOCAL_DATE = LocalDate.of(2060, 5, 21);
    private static final String LOCAL_DATE_READABLE = "May 21 2060";
    private static final String INVALID_DATE_STRING = "invalid-date";
    private static final LocalDate START_DATE = LocalDate.of(2024, 12, 1);
    private static final LocalDate END_DATE = LocalDate.of(2024, 12, 3);

    private static final String VALID_ZONED_DATE_TIME_STRING = "2060-05-21T15:23:20.142Z";
    private static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.parse(VALID_ZONED_DATE_TIME_STRING);

    private static final int DAYS_BETWEEN_DATES = 2;
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2024, 1, 1, 10, 0);
    private static final String INVALID_DATE_TIME_STRING = "invalid-date-time";

    private static final String VALID_DATE_STRING = "2060-05-21";

    private static final String VALID_ISO_DATE_TIME = "2060-05-21T15:23:20.142Z";
    private static final String INVALID_ISO_DATE_TIME = "invalid-date-time";

    @Test
    void givenNullDateString_whenParseToLocalDate_thenReturnsNull() {
        assertNull(DateUtils.parseToLocalDate(null));
    }

    @Test
    void givenNullDateTimeString_whenParseToZonedDateTime_thenReturnsNull() {
        assertNull(DateUtils.parseToZonedDateTime(null));
    }

    @Test
    void givenInvalidDateString_whenParseToLocalDate_thenThrowsIllegalArgumentExceptionWithMessage() {
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> DateUtils.parseToLocalDate(INVALID_DATE_STRING));
        assertEquals("Invalid date format: invalid-date", exception.getMessage());
    }

    @Test
    void givenInvalidDateTimeString_whenParseToZonedDateTime_thenThrowsIllegalArgumentExceptionWithMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                                           () -> DateUtils.parseToZonedDateTime(INVALID_DATE_TIME_STRING));
        assertEquals("Invalid date-time format: invalid-date-time", exception.getMessage());
    }

    @Test
    void givenValidDateString_whenParseToLocalDate_thenReturnsCorrectLocalDate() {

        LocalDate localDate = DateUtils.parseToLocalDate(LOCAL_DATE_STRING);

        assertEquals(LOCAL_DATE, localDate);
    }

    @Test
    void givenValidLocalDate_whenFormatLocalDate_thenReturnsCorrectString() {
        LocalDate localDate = LOCAL_DATE;

        String formattedDate = DateUtils.formatLocalDate(localDate);

        assertEquals(LOCAL_DATE_STRING, formattedDate);
    }

    @Test
    void givenNullDate_whenFormatLocalDate_thenReturnsNull() {
        LocalDate nullDate = null;

        String formattedDate = DateUtils.formatLocalDate(nullDate);

        assertNull(formattedDate);
    }

    @Test
    void givenValidZonedDateTimeString_whenParseToZonedDateTime_thenReturnsCorrectZonedDateTime() {

        ZonedDateTime zonedDateTime = DateUtils.parseToZonedDateTime(VALID_ZONED_DATE_TIME_STRING);

        assertEquals(ZONED_DATE_TIME, zonedDateTime);
    }

    @Test
    void givenInvalidDateTimeString_whenParseToZonedDateTime_thenThrowsIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> DateUtils.parseToZonedDateTime(INVALID_DATE_TIME_STRING));
    }

    @Test
    void givenValidZonedDateTime_whenFormatZonedDateTime_thenReturnsCorrectString() {

        String formattedDateTime = DateUtils.formatZonedDateTime(ZONED_DATE_TIME);

        assertEquals(VALID_ZONED_DATE_TIME_STRING, formattedDateTime);
    }

    @Test
    void givenNullZonedDateTime_whenFormatZonedDateTime_thenReturnsNull() {
        ZonedDateTime nullDateTime = null;

        String formattedDateTime = DateUtils.formatZonedDateTime(nullDateTime);

        assertNull(formattedDateTime);
    }

    @Test
    void givenNullDateString_whenIsValidLocalDateOrNull_thenReturnsTrue() {
        assertTrue(DateUtils.isValidLocalDateOrNull(null));
    }

    @Test
    void givenEmptyDateString_whenIsValidLocalDateOrNull_thenReturnsFalse() {
        assertFalse(DateUtils.isValidLocalDateOrNull(""));
    }

    @Test
    void givenValidDateString_whenIsValidLocalDateOrNull_thenReturnsTrue() {
        assertTrue(DateUtils.isValidLocalDateOrNull(VALID_DATE_STRING));
    }

    @Test
    void givenInvalidDateString_whenIsValidLocalDateOrNull_thenReturnsFalse() {
        assertFalse(DateUtils.isValidLocalDateOrNull(INVALID_DATE_STRING));
    }

    @Test
    void givenValidISODateTime_whenIsValidISODateTime_thenReturnsTrue() {
        assertTrue(DateUtils.isValidISODateTime(VALID_ISO_DATE_TIME));
    }

    @Test
    void givenInvalidISODateTime_whenIsValidISODateTime_thenReturnsFalse() {
        assertFalse(DateUtils.isValidISODateTime(INVALID_ISO_DATE_TIME));
    }

    @Test
    void givenEmptyISODateTime_whenIsValidISODateTime_thenReturnsFalse() {
        assertFalse(DateUtils.isValidISODateTime(""));
    }

    @Test
    void givenValidStartAndEndDates_whenGenerateDatesBetween_thenReturnsListOfDates() {
        List<LocalDate> result = DateUtils.generateDatesBetween(START_DATE, END_DATE);

        assertEquals(3, result.size());
        assertEquals(START_DATE, result.get(0));
        assertEquals(END_DATE, result.get(2));
    }

    @Test
    void givenStartDateEqualsEndDate_whenGenerateDatesBetween_thenReturnsSingleDate() {
        List<LocalDate> result = DateUtils.generateDatesBetween(START_DATE, START_DATE);

        assertEquals(1, result.size());
        assertEquals(START_DATE, result.get(0));
    }

    @Test
    void givenStartDateAfterEndDate_whenGenerateDatesBetween_thenReturnsEmptyList() {
        List<LocalDate> result = DateUtils.generateDatesBetween(END_DATE, START_DATE);

        assertTrue(result.isEmpty());
    }

    @Test
    void givenLeapYear_whenGenerateDatesBetween_thenIncludesFebruary29() {
        LocalDate startDate = LocalDate.of(2024, 2, 28);
        LocalDate endDate = LocalDate.of(2024, 3, 1);

        List<LocalDate> result = DateUtils.generateDatesBetween(startDate, endDate);

        assertEquals(3, result.size());
        assertTrue(result.contains(LocalDate.of(2024, 2, 29)));
    }

    @Test
    void givenNullLocalDate_whenConvertToDate_thenReturnsNull() {
        LocalDate localDate = null;

        Date result = DateUtils.convertToDate(localDate);

        assertNull(result);
    }

    @Test
    void givenLocalDate_whenConvertToDate_thenReturnsDate() {
        Date result = DateUtils.convertToDate(LOCAL_DATE);

        assertNotNull(result);
        assertEquals(Date.valueOf(LOCAL_DATE), result);
    }

    @Test
    void givenValidZonedDateTime_whenConvertToTimestamp_thenReturnsTimestamp() {
        Timestamp result = DateUtils.convertToTimestamp(ZONED_DATE_TIME);

        assertNotNull(result);
        assertEquals(Timestamp.from(ZONED_DATE_TIME.toInstant()), result);
    }

    @Test
    void givenNullZonedDateTime_whenConvertToTimestamp_thenReturnsNull() {
        Timestamp result = DateUtils.convertToTimestamp(null);

        assertNull(result);
    }

    @Test
    void givenFutureZonedDateTime_whenConvertToTimestamp_thenReturnsCorrectTimestamp() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2100, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));

        Timestamp result = DateUtils.convertToTimestamp(zonedDateTime);

        assertNotNull(result);
        assertEquals(Timestamp.from(zonedDateTime.toInstant()), result);
    }

    @Test
    void givenValidDate_whenConvertToLocalDate_thenReturnsLocalDate() {
        Date aDate = Date.valueOf(LOCAL_DATE);

        LocalDate result = DateUtils.convertToLocalDate(aDate);

        assertNotNull(result);
        assertEquals(LOCAL_DATE, result);
    }

    @Test
    void givenNullDate_whenConvertToLocalDate_thenReturnsNull() {
        LocalDate result = DateUtils.convertToLocalDate(null);

        assertNull(result);
    }

    @Test
    void givenValidTimestamp_whenConvertToZonedDateTime_thenReturnsZonedDateTime() {
        Timestamp timestamp = Timestamp.from(ZONED_DATE_TIME.toInstant());

        ZonedDateTime result = DateUtils.convertToZonedDateTime(timestamp);

        assertNotNull(result);
        assertEquals(ZONED_DATE_TIME.toInstant(), result.toInstant());
        assertEquals(ZoneId.systemDefault(), result.getZone());
    }

    @Test
    void givenNullTimestamp_whenConvertToZonedDateTime_thenReturnsNull() {
        ZonedDateTime result = DateUtils.convertToZonedDateTime(null);

        assertNull(result);
    }

    @Test
    void givenValidZonedDateTime_whenFormatZonedDateTimeToReadableDate_thenReturnsFormattedString() {
        String result = DateUtils.formatZonedDateTimeToReadableDate(ZONED_DATE_TIME);

        assertNotNull(result);
        assertEquals(LOCAL_DATE_READABLE, result);
    }

    @Test
    void givenValidLocalDate_whenFormatLocalDateTimeToReadableDate_thenReturnsFormattedString() {
        String result = DateUtils.formatLocalDateTimeToReadableDate(LOCAL_DATE);

        assertNotNull(result);
        assertEquals(LOCAL_DATE_READABLE, result);
    }

    @Test
    void givenNullLocalDate_whenFormatLocalDateTimeToReadableDate_thenReturnsNull() {
        String result = DateUtils.formatLocalDateTimeToReadableDate(null);

        assertNull(result);
    }

    @Test
    void givenStartAndEndDates_whenNumberOfDaysBetween_thenReturnsCorrectNumberOfDays() {
        int result = DateUtils.numberOfDaysBetween(START_DATE, END_DATE);

        assertEquals(DAYS_BETWEEN_DATES, result);
    }

    @Test
    void givenStartAndEndDatesWithSameDay_whenNumberOfDaysBetween_thenReturnsZero() {
        int result = DateUtils.numberOfDaysBetween(START_DATE, START_DATE);

        assertEquals(0, result);
    }

    @Test
    void givenStartDateAndPositiveInterval_whenCalculateTimestamp_thenReturnsCorrectDate() {
        int intervalInDays = 5;
        LocalDateTime expected = LOCAL_DATE_TIME.plusDays(intervalInDays);

        LocalDateTime result = DateUtils.calculateTimestamp(LOCAL_DATE_TIME, intervalInDays);

        assertEquals(expected, result);
    }

    @Test
    void givenStartDateAndZeroInterval_whenCalculateTimestamp_thenReturnsSameDate() {
        int intervalInDays = 0;

        LocalDateTime result = DateUtils.calculateTimestamp(LOCAL_DATE_TIME, intervalInDays);

        assertEquals(LOCAL_DATE_TIME, result);
    }

    @Test
    void givenLocalDate_whenToLocalDateTime_thenReturnsLocalDateTimeAtStartOfDay() {
        LocalDateTime result = DateUtils.toLocalDateTime(LOCAL_DATE);
        LocalDateTime expected = LOCAL_DATE.atStartOfDay();

        assertEquals(expected, result);
    }

    @Test
    void givenNullLocalDate_whenToLocalDateTime_thenReturnsNull() {
        assertThrows(IllegalArgumentException.class, () -> DateUtils.toLocalDateTime(null));
    }
}