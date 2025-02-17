package ca.ulaval.glo4002.application.interfaces.rest.dto.responses;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UnknownErrorResponse {
    public final String message;

    @JsonCreator
    public UnknownErrorResponse(String message) {
        this.message = message;
    }
}
