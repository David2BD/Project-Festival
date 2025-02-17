package ca.ulaval.glo4002.application.domain.order;

public class InvalidOrderNumberException extends RuntimeException {
    public InvalidOrderNumberException(String message) {
        super(message);
    }
}
