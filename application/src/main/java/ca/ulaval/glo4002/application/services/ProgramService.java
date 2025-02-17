package ca.ulaval.glo4002.application.services;

import ca.ulaval.glo4002.application.domain.ArtistsListSource;
import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.ConfirmedProgram;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.ScheduleSimulation;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.ArtistSchedulingStrategy;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.SchedulingType;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.selection.ArtistSelectionStrategy;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.selection.SelectionCriteria;
import ca.ulaval.glo4002.application.infrastructure.artistSource.ExcelArtistsListSource;
import ca.ulaval.glo4002.application.services.requests.ProgramConfirmRequest;
import ca.ulaval.glo4002.application.services.scheduleSimulation.ArtistSchedulingStrategyFactory;
import ca.ulaval.glo4002.application.services.scheduleSimulation.ArtistSelectionStrategyFactory;
import ca.ulaval.glo4002.application.utils.MoneyUtils;
import jakarta.inject.Inject;

import java.util.List;

public class ProgramService {
    private final FestivalRepository festivalRepository;
    private final Festival festival;

    private final ArtistsListSource artistsListSource;
    private final ArtistSelectionStrategyFactory selectionFactory;
    private final ArtistSchedulingStrategyFactory schedulingFactory;

    @Inject
    public ProgramService(
            FestivalRepository festivalRepository, ArtistSelectionStrategyFactory selectionFactory,
            ArtistSchedulingStrategyFactory schedulingFactory
    ) {
        this.festivalRepository = festivalRepository;
        this.festival = festivalRepository.getFestival();
        this.selectionFactory = selectionFactory;
        this.schedulingFactory = schedulingFactory;
        this.artistsListSource = new ExcelArtistsListSource();
    }

    public ScheduleSimulation getSimulation(
            SelectionCriteria criteria, SchedulingType scheduling, String headlinerBudget, Integer headlinerLimit
    ) {
        MoneyAmount headlinerBudgetMoney = MoneyUtils.parseMoneyAmount(headlinerBudget);
        List<Artist> artists = artistsListSource.extractAvailableArtists();
        ArtistSelectionStrategy selectionStrategy = selectionFactory.create(criteria);
        ArtistSchedulingStrategy schedulingStrategy =
                schedulingFactory.create(SchedulingType.fromString(String.valueOf(scheduling)));

        return festival.getScheduleSimulation(artists, selectionStrategy, schedulingStrategy, headlinerBudgetMoney,
                                              headlinerLimit);
    }

    public void confirmProgram(ProgramConfirmRequest programConfirmRequest) {
        List<Artist> artists = artistsListSource.extractAvailableArtists();
        ScheduleSimulation scheduleSimulation =
                festival.getScheduleSimulation(artists, selectionFactory.create(programConfirmRequest.getCriteria()),
                                               schedulingFactory.create(programConfirmRequest.getSchedulingType()),
                                               programConfirmRequest.getHeadlinerBudget(),
                                               programConfirmRequest.getHeadlinerLimit());

        festival.confirmProgram(scheduleSimulation, programConfirmRequest.getConfirmationDate());
        festivalRepository.saveFestival(festival);
    }

    public ConfirmedProgram getConfirmedProgram() {
        return festival.getConfirmedProgram();
    }

}
