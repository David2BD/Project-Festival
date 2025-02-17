package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.transport.Transport;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.TransportResponseDTO;
import ca.ulaval.glo4002.application.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransportMapperTest {

    private static final LocalDate AN_EVENT_DATE = LocalDate.of(2060, 7, 17);
    private static final String FORMATTED_DATE = DateUtils.formatLocalDate(AN_EVENT_DATE);
    private static final ShuttleType A_SHUTTLE_TYPE = ShuttleType.SPACE_X;
    private static final List<String> PASS_NUMBER_LIST = List.of("123L", "456L");

    @Mock
    private Transport transportMock;

    @Mock
    private PassNumber passNumberMock1;

    @Mock
    private PassNumber passNumberMock2;

    @InjectMocks
    private TransportMapper transportMapper;

    @Test
    void givenTransport_whenToTransportDTO_thenReturnsCorrectTransportResponseDTO() {
        when(transportMock.getEventDate()).thenReturn(AN_EVENT_DATE);
        when(transportMock.getShuttleType()).thenReturn(A_SHUTTLE_TYPE);
        when(passNumberMock1.getNumber()).thenReturn(PASS_NUMBER_LIST.get(0));
        when(passNumberMock2.getNumber()).thenReturn(PASS_NUMBER_LIST.get(1));
        when(transportMock.getPassNumbers()).thenReturn(List.of(passNumberMock1, passNumberMock2));

        TransportResponseDTO result = transportMapper.toTransportDTO(transportMock);

        assertEquals(FORMATTED_DATE, result.date());
        assertEquals(A_SHUTTLE_TYPE.toString(), result.shuttleType());
        assertEquals(PASS_NUMBER_LIST, result.passengers());
    }
}
