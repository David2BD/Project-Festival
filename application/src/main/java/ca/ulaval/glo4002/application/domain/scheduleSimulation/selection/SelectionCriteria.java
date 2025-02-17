package ca.ulaval.glo4002.application.domain.scheduleSimulation.selection;

public enum SelectionCriteria {
    MINIMIZE_COST,
    HEADLINER_BUDGET,
    HEADLINER_NUMBER;

    public static SelectionCriteria fromString(String criteria) {
        if (criteria == null || criteria.isBlank()) {
            throw new IllegalArgumentException("Criteria cannot be null or blank");
        }
        switch (criteria.trim()) {
            case "MinimizeCost":
                return MINIMIZE_COST;
            case "HeadlinerBudget":
                return HEADLINER_BUDGET;
            case "HeadlinerNumber":
                return HEADLINER_NUMBER;
            default:
                throw new IllegalArgumentException("Invalid selection criteria: " + criteria);
        }
    }

}
