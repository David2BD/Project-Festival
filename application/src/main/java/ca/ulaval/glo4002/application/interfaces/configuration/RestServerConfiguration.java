package ca.ulaval.glo4002.application.interfaces.configuration;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenProvider;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenReportGenerator;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenTankProducer;
import ca.ulaval.glo4002.application.domain.pass.PassFactory;
import ca.ulaval.glo4002.application.domain.pass.PassOxygenSelector;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.selection.ArtistSelectionStrategy;
import ca.ulaval.glo4002.application.infrastructure.initialization.PersistenceInitializer;
import ca.ulaval.glo4002.application.infrastructure.sqLite.OrderMapperSQLite;
import ca.ulaval.glo4002.application.infrastructure.sqLite.PassMapperSQLite;
import ca.ulaval.glo4002.application.interfaces.rest.dto.mappers.OrderMapper;
import ca.ulaval.glo4002.application.interfaces.rest.dto.mappers.PassMapper;
import ca.ulaval.glo4002.application.interfaces.rest.dto.mappers.ProgramMapper;
import ca.ulaval.glo4002.application.interfaces.rest.dto.mappers.ShuttleManifestMapper;
import ca.ulaval.glo4002.application.interfaces.rest.dto.mappers.SimulationReportMapper;
import ca.ulaval.glo4002.application.interfaces.rest.dto.mappers.TransportMapper;
import ca.ulaval.glo4002.application.interfaces.rest.exceptionsMappers.*;
import ca.ulaval.glo4002.application.interfaces.rest.validators.OrderRequestValidator;
import ca.ulaval.glo4002.application.interfaces.rest.validators.PassRequestValidator;
import ca.ulaval.glo4002.application.interfaces.rest.validators.ProgramRequestValidator;
import ca.ulaval.glo4002.application.services.OrderService;
import ca.ulaval.glo4002.application.services.ProgramService;
import ca.ulaval.glo4002.application.services.ReportService;
import ca.ulaval.glo4002.application.services.ShuttleManifestService;
import ca.ulaval.glo4002.application.services.scheduleSimulation.ArtistSchedulingStrategyFactory;
import ca.ulaval.glo4002.application.services.scheduleSimulation.ArtistSelectionStrategyFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.sql.SQLException;

public class RestServerConfiguration extends AbstractBinder {
    @Override
    protected void configure() {
        // Bind services and dependencies
        bindAsContract(OrderService.class);
        bindAsContract(ReportService.class);
        bindAsContract(ProgramService.class);
        bindAsContract(ShuttleManifestService.class);
        bindAsContract(OrderMapper.class);
        bindAsContract(PassMapper.class);
        bindAsContract(PassMapperSQLite.class);
        bindAsContract(OrderMapperSQLite.class);
        bindAsContract(TransportMapper.class);
        bindAsContract(ShuttleManifestMapper.class);
        bindAsContract(SimulationReportMapper.class);
        bindAsContract(PassOxygenSelector.class);
        bindAsContract(OxygenProvider.class);
        bindAsContract(OxygenTankProducer.class);

        bindAsContract(OrderRequestValidator.class);
        bindAsContract(PassRequestValidator.class);
        bindAsContract(ProgramRequestValidator.class);
        bindAsContract(ProgramMapper.class);
        bindAsContract(PassFactory.class);
        bindAsContract(OxygenReportGenerator.class);

        // Bind exception mappers
        bindAsContract(InvalidOrderDateExceptionMapper.class);
        bindAsContract(DomainInvalidFormatExceptionMapper.class);
        bindAsContract(InterfacesInvalidFormatExceptionMapper.class);
        bindAsContract(InvalidEventDateExceptionMapper.class);
        bindAsContract(IllegalArgumentExceptionMapper.class);
        bindAsContract(OrderNotFoundExceptionMapper.class);
        bindAsContract(PassCreationExceptionMapper.class);
        bindAsContract(AlreadyConfirmedExceptionMapper.class);
        bindAsContract(InvalidConfirmationDateExceptionMapper.class);
        bindAsContract(OrderRequestValidator.class);
        bindAsContract(PassRequestValidator.class);
        bindAsContract(ProgramRequestValidator.class);
        bindAsContract(ProgramMapper.class);
        bindAsContract(ArtistSelectionStrategy.class);
        bindAsContract(ArtistSelectionStrategy.class);
        bindAsContract(ArtistSchedulingStrategyFactory.class);
        bindAsContract(ArtistSelectionStrategyFactory.class);

        // Initialize and bind Festival and FestivalRepository
        FestivalRepository festivalRepository = PersistenceInitializer.getFestivalRepository();
        Festival festival = null;
        try {
            festival = PersistenceInitializer.initialize();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        bind(festival).to(Festival.class);
        bind(festivalRepository).to(FestivalRepository.class);

    }
}
