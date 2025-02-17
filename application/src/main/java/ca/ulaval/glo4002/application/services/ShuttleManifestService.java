package ca.ulaval.glo4002.application.services;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import ca.ulaval.glo4002.application.domain.transport.Transport;
import ca.ulaval.glo4002.application.domain.transport.manifest.ShuttleManifest;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ShuttleManifestService {
    private final FestivalRepository festivalRepository;

    @Inject
    public ShuttleManifestService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    public ShuttleManifest generateShuttleManifest(LocalDate date) {
        Festival festival = festivalRepository.getFestival();
        Map<LocalDate, List<Transport>> transportsByDate;
        transportsByDate = festival.generateShuttleManifest(date);
        return new ShuttleManifest(transportsByDate);
    }

}
