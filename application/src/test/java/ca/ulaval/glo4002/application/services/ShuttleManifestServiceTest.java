package ca.ulaval.glo4002.application.services;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import ca.ulaval.glo4002.application.domain.transport.manifest.ShuttleManifest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShuttleManifestServiceTest {
    private ShuttleManifestService shuttleManifestService;

    private static final LocalDate A_DATE = LocalDate.of(2060, 7, 18);

    @Mock
    private FestivalRepository festivalRepositoryMock;

    @Mock
    private Festival festivalMock;

    @BeforeEach
    void setUp() {
        shuttleManifestService = new ShuttleManifestService(festivalRepositoryMock);

        when(festivalRepositoryMock.getFestival()).thenReturn(festivalMock);
    }

    @Test
    void givenAnyDate_whenGeneratingShuttleManifest_thenFestivalIsFetched() {
        shuttleManifestService.generateShuttleManifest(A_DATE);

        verify(festivalRepositoryMock).getFestival();
    }

    @Test
    void givenAnyDate_whenGeneratingShuttleManifest_thenDelegateToFestival() {
        shuttleManifestService.generateShuttleManifest(A_DATE);

        verify(festivalMock).generateShuttleManifest(A_DATE);
    }

    @Test
    void givenAnyDate_whenGeneratingShuttleManifest_thenReturnShuttleManifest() {
        ShuttleManifest shuttleManifest = shuttleManifestService.generateShuttleManifest(A_DATE);

        assertNotNull(shuttleManifest);
    }
}

