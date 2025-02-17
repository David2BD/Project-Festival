package ca.ulaval.glo4002.application.interfaces.rest.dto.responses;

import java.util.List;

public record TransportResponseDTO(String date, String shuttleType, List<String> passengers) {}

