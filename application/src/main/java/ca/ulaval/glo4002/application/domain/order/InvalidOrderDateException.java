package ca.ulaval.glo4002.application.domain.order;

public class InvalidOrderDateException extends RuntimeException {
    public InvalidOrderDateException(String message) {
        super(message);
    }
}
