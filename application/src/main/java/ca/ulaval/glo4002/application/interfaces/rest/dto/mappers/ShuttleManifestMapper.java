package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.transport.manifest.ShuttleManifest;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ShuttleManifestResponseDTO;
import jakarta.inject.Inject;

public class ShuttleManifestMapper {
    private final TransportMapper transportMapper;

    @Inject
    public ShuttleManifestMapper(TransportMapper transportMapper) {
        this.transportMapper = transportMapper;
    }

    public ShuttleManifestResponseDTO toShuttleManifestResponseDTO(ShuttleManifest shuttleManifest) {
        return new ShuttleManifestResponseDTO(
                shuttleManifest.departures().stream().map(transportMapper::toTransportDTO).toList(),
                shuttleManifest.arrivals().stream().map(transportMapper::toTransportDTO).toList());
    }
}
