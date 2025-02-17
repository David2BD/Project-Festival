package ca.ulaval.glo4002.application.interfaces.rest.dto.requests;

public record PassRequestDTO(long passNumber, String passCategory, String passOption, String eventDate) {}
