package ca.ulaval.glo4002.application.domain.festival;

public class InvalidConfirmationDateException extends RuntimeException {
    public InvalidConfirmationDateException(String message) {
        super(message);
    }
}

