package ca.ulaval.glo4002.application.services.scheduleSimulation;

import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.ArtistSchedulingStrategy;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.CrescendoStrategy;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.RollercoasterStrategy;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.SchedulingType;

public class ArtistSchedulingStrategyFactory {
    public ArtistSchedulingStrategy create(SchedulingType schedulingType) {
        return switch (schedulingType) {
            case CRESCENDO -> new CrescendoStrategy();
            case ROLLERCOASTER -> new RollercoasterStrategy();
        };
    }
}
