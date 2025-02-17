package ca.ulaval.glo4002.application.interfaces.rest.dto.responses;

import java.util.List;

public record PassResponseDTO(String passNumber, String passCategory, String passOption, List<String> eventDates) {}