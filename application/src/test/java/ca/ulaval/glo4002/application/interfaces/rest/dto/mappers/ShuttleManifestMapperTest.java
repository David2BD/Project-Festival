package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.transport.Transport;
import ca.ulaval.glo4002.application.domain.transport.manifest.ShuttleManifest;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ShuttleManifestResponseDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.TransportResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShuttleManifestMapperTest {

    @Mock
    private TransportMapper transportMapperMock;

    @Mock
    private ShuttleManifest shuttleManifestMock;

    @Mock
    private Transport transportDepartureMock;

    @Mock
    private Transport transportArrivalMock;

    @Mock
    private TransportResponseDTO transportResponseDepartureMock;

    @Mock
    private TransportResponseDTO transportResponseArrivalMock;

    @InjectMocks
    private ShuttleManifestMapper shuttleManifestMapper;

    @Test
    void givenShuttleManifest_whenToShuttleManifestResponseDTO_thenMapsToCorrectResponseDTO() {
        when(shuttleManifestMock.departures()).thenReturn(List.of(transportDepartureMock));
        when(shuttleManifestMock.arrivals()).thenReturn(List.of(transportArrivalMock));
        when(transportMapperMock.toTransportDTO(transportDepartureMock)).thenReturn(transportResponseDepartureMock);
        when(transportMapperMock.toTransportDTO(transportArrivalMock)).thenReturn(transportResponseArrivalMock);

        ShuttleManifestResponseDTO result = shuttleManifestMapper.toShuttleManifestResponseDTO(shuttleManifestMock);

        assertEquals(List.of(transportResponseDepartureMock), result.departures());
        assertEquals(List.of(transportResponseArrivalMock), result.arrivals());
    }
}
