package ca.ulaval.glo4002.application.domain.festival;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
