package ca.ulaval.glo4002.application.domain.pass.exceptions;

public class InvalidEventDateException extends RuntimeException {
    public InvalidEventDateException(String message) {
        super(message);
    }
}
