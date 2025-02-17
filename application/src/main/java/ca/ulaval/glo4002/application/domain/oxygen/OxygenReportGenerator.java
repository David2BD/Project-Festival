package ca.ulaval.glo4002.application.domain.oxygen;

import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.reports.OxygenReport;
import jakarta.inject.Inject;

import java.util.List;

public class OxygenReportGenerator {

    private final OxygenProvider oxygenProvider;

    @Inject
    public OxygenReportGenerator() {
        this.oxygenProvider = new OxygenProvider();
    }

    public OxygenReportGenerator(OxygenProvider oxygenProvider) {
        this.oxygenProvider = oxygenProvider;
    }

    public OxygenReport generateOxygenReport(List<OxygenRequester> oxygenRequesters, FestivalDates festivalDates) {
        prepareOxygen(oxygenRequesters, festivalDates);
        return new OxygenReport(oxygenProvider.getOxygenInventory(), oxygenProvider.getOxygenLogEntries());
    }

    private void prepareOxygen(List<OxygenRequester> oxygenRequesters, FestivalDates festivalDates) {
        oxygenProvider.supplyOxygen(oxygenRequesters, festivalDates);
    }
}