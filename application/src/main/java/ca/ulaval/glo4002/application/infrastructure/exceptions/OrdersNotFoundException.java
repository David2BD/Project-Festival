package ca.ulaval.glo4002.application.infrastructure.exceptions;

public class OrdersNotFoundException extends RuntimeException {
    public OrdersNotFoundException(String message) {
        super(message);
    }

    public OrdersNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
