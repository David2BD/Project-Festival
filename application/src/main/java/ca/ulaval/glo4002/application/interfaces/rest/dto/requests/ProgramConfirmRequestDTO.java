package ca.ulaval.glo4002.application.interfaces.rest.dto.requests;

public record ProgramConfirmRequestDTO(String confirmationDate, String criteria, String scheduling,
                                       float headlinerBudget, int headlinerLimit) {}
