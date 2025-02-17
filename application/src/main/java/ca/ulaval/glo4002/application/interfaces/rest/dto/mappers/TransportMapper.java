package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.transport.Transport;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.TransportResponseDTO;
import ca.ulaval.glo4002.application.utils.DateUtils;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

public class TransportMapper {

    @Inject
    public TransportMapper() {
    }

    public TransportResponseDTO toTransportDTO(Transport transport) {
        String transportDateAsString = DateUtils.formatLocalDate(transport.getEventDate());
        List<String> passNumbers =
                transport.getPassNumbers().stream().map(PassNumber::getNumber).collect(Collectors.toList());

        return new TransportResponseDTO(transportDateAsString, transport.getShuttleType().toString(), passNumbers);
    }
}
