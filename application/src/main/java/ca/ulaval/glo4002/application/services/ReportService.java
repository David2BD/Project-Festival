package ca.ulaval.glo4002.application.services;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import ca.ulaval.glo4002.application.domain.reports.OxygenReport;
import ca.ulaval.glo4002.application.domain.reports.ProfitReport;
import jakarta.inject.Inject;

public class ReportService {
    private final FestivalRepository festivalRepository;

    @Inject
    public ReportService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    public OxygenReport getOxygenReport() {
        Festival festival = festivalRepository.getFestival();
        return festival.getOxygenReport();
    }

    public ProfitReport getProfitReport() {
        Festival festival = festivalRepository.getFestival();
        return festival.generateProfitReport();
    }
}
