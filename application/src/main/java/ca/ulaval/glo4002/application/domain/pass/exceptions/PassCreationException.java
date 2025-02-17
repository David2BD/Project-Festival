package ca.ulaval.glo4002.application.domain.pass.exceptions;

public class  PassCreationException extends RuntimeException {
    public PassCreationException(String message) {
        super(message);
    }
}