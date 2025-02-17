package ca.ulaval.glo4002.application.utils;

public class InvalidFormatException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "invalid format";

    public InvalidFormatException(String message) {
        super(message);
    }

    public InvalidFormatException() {
        super(DEFAULT_MESSAGE);
    }
}
