package ca.ulaval.glo4002.application.interfaces.rest.dto.responses;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class ConstraintValidationResponse {
    public final String message;
    public final List<String> validations;

    @JsonCreator
    public ConstraintValidationResponse(String message, List<String> validations) {
        this.message = message;
        this.validations = validations;
    }
}
