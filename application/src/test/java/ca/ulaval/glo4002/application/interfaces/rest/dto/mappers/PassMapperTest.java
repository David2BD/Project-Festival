package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.domain.pass.PassFactory;
import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategory;
import ca.ulaval.glo4002.application.domain.pass.categories.PassCategoryTypes;
import ca.ulaval.glo4002.application.domain.pass.categories.VipPassCategory;
import ca.ulaval.glo4002.application.domain.pass.options.DailyPassOption;
import ca.ulaval.glo4002.application.domain.pass.options.PassOption;
import ca.ulaval.glo4002.application.domain.pass.options.PassOptionTypes;
import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.PassRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.PassResponseDTO;
import ca.ulaval.glo4002.application.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassMapperTest {
    private static final String A_PASS_NUMBER = "123L";
    private static final PassCategoryTypes A_PASS_CATEGORY = PassCategoryTypes.VIP;
    private static final String A_PASS_OPTION_REQUEST = "passe_journaliere";
    private static final String A_PASS_CATEGORY_REQUEST = "VIP";
    private static final String ANY_EVENT_DATE_REQUEST = "2060-07-17";
    private static final LocalDate AN_EVENT_DATE = LocalDate.of(2060, 7, 17);
    private static final String FORMATTED_EVENT_DATE = DateUtils.formatLocalDate(AN_EVENT_DATE);
    private static final List<LocalDate> EVENT_DATES = List.of(AN_EVENT_DATE, AN_EVENT_DATE.plusDays(1));
    private static final PassCategory ANY_CATEGORY_PASS = new VipPassCategory();
    private static final PassOption ANY_OPTION_PASS = new DailyPassOption(AN_EVENT_DATE);
    private static final Pass ANY_PASS = new Pass(ANY_CATEGORY_PASS, ANY_OPTION_PASS);
    private static final PassOptionTypes ANY_PASS_OPTION_TYPE = PassOptionTypes.DAILY;
    private final List<PassRequestDTO> passRequestDtos = new ArrayList<>();
    private final List<Pass> passes = new ArrayList<>();

    private PassMapper passMapper;

    @Mock
    PassRequestDTO passRequestDtoMock;

    @Mock
    private PassFactory passFactoryMock;

    @Mock
    private Festival festivalMock;

    @Mock
    private Pass passMock;

    @Mock
    private PassNumber passNumberMock;

    @BeforeEach
    void setUp() {

        passMapper = new PassMapper(passFactoryMock, festivalMock);

        lenient().when(passMock.getPassNumber()).thenReturn(passNumberMock);
        lenient().when(passNumberMock.getNumber()).thenReturn(A_PASS_NUMBER);
        lenient().when(passMock.getPassCategory()).thenReturn(A_PASS_CATEGORY);
        lenient().when(passMock.getPassOption()).thenReturn(ANY_PASS_OPTION_TYPE);

        lenient().when(
                        passFactoryMock.createPass(A_PASS_CATEGORY_REQUEST, A_PASS_OPTION_REQUEST, ANY_EVENT_DATE_REQUEST))
                .thenReturn(ANY_PASS);
        lenient().when(passRequestDtoMock.passCategory()).thenReturn(A_PASS_CATEGORY_REQUEST);
        lenient().when(passRequestDtoMock.passOption()).thenReturn(A_PASS_OPTION_REQUEST);
        lenient().when(passRequestDtoMock.eventDate()).thenReturn(ANY_EVENT_DATE_REQUEST);
    }

    @Test
    void givenPassRequestDTOList_whenToPasses_thenReturnsListOfPasses() {
        passRequestDtos.add(passRequestDtoMock);

        List<Pass> result = passMapper.toPasses(passRequestDtos);
        Pass mappedPass = result.get(0);

        assertEquals(1, result.size());
        assertEquals(A_PASS_CATEGORY, mappedPass.getPassCategory());
        assertEquals(ANY_PASS_OPTION_TYPE, mappedPass.getPassOption());
        assertEquals(LocalDate.parse(ANY_EVENT_DATE_REQUEST), mappedPass.getEventDate());
        verify(passFactoryMock).createPass(A_PASS_CATEGORY_REQUEST, A_PASS_OPTION_REQUEST, ANY_EVENT_DATE_REQUEST);
    }

    @Test
    void givenPassList_whenToPassResponseDTOs_thenReturnsListOfPassResponseDTOs() {
        passes.add(passMock);
        when(passMock.getEventDates(any(), any())).thenReturn(EVENT_DATES);

        List<PassResponseDTO> result = passMapper.toPassResponseDTOs(passes);
        PassResponseDTO passResponseDTO = result.get(0);

        assertEquals(1, result.size());
        assertEquals(A_PASS_NUMBER, passResponseDTO.passNumber());
        assertEquals(A_PASS_CATEGORY.name(), passResponseDTO.passCategory());
        assertEquals(A_PASS_OPTION_REQUEST, passResponseDTO.passOption());
        assertEquals(List.of(FORMATTED_EVENT_DATE, DateUtils.formatLocalDate(AN_EVENT_DATE.plusDays(1))),
                     passResponseDTO.eventDates());
    }
}
