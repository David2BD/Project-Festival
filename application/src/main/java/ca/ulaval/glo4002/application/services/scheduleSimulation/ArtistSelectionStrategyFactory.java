package ca.ulaval.glo4002.application.services.scheduleSimulation;

import ca.ulaval.glo4002.application.domain.scheduleSimulation.selection.*;

public class ArtistSelectionStrategyFactory {
    public ArtistSelectionStrategy create(SelectionCriteria selectionCriteria){
        return switch (selectionCriteria) {
            case MINIMIZE_COST -> new MinimizeCostStrategy();
            case HEADLINER_BUDGET -> new HeadlinerBudgetStrategy();
            case HEADLINER_NUMBER -> new HeadlinerNumberStrategy();
        };
    }
}
