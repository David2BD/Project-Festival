package ca.ulaval.glo4002.application.services;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @Mock
    private Festival festivalMock;

    @Mock
    private FestivalRepository festivalRepositoryMock;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        when(festivalRepositoryMock.getFestival()).thenReturn(festivalMock);
    }

    @Test
    void whenGetOxygenReport_thenDelegateToFestival() {
        reportService.getOxygenReport();

        verify(festivalRepositoryMock).getFestival();
        verify(festivalMock).getOxygenReport();
    }

    @Test
    void whenGetProfitsReport_thenDelegateToFestival() {
        reportService.getProfitReport();

        verify(festivalRepositoryMock).getFestival();
        verify(festivalMock).generateProfitReport();
    }

}
