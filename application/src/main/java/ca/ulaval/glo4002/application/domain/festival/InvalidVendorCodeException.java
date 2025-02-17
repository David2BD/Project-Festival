package ca.ulaval.glo4002.application.domain.festival;

public class InvalidVendorCodeException extends RuntimeException {
    public InvalidVendorCodeException(String message) {
        super(message);
    }
}

