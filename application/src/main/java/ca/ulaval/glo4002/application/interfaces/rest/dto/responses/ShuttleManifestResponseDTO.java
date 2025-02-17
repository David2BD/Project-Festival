package ca.ulaval.glo4002.application.interfaces.rest.dto.responses;

import java.util.List;

public record ShuttleManifestResponseDTO(List<TransportResponseDTO> departures, List<TransportResponseDTO> arrivals) {

}