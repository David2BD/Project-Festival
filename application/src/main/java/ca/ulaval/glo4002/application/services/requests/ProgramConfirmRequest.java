package ca.ulaval.glo4002.application.services.requests;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling.SchedulingType;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.selection.SelectionCriteria;

import java.time.LocalDate;

public class ProgramConfirmRequest {
    private final LocalDate confirmationDate;
    private final SelectionCriteria criteria;
    private final SchedulingType schedulingType;
    private final MoneyAmount headlinerBudget;
    private final int headlinerLimit;

    public ProgramConfirmRequest(
            LocalDate confirmationDate, SelectionCriteria criteria, SchedulingType schedulingType,
            MoneyAmount headlinerBudget, int headlinerLimit
    ) {
        this.confirmationDate = confirmationDate;
        this.criteria = criteria;
        this.schedulingType = schedulingType;
        this.headlinerBudget = headlinerBudget;
        this.headlinerLimit = headlinerLimit;
    }

    public int getHeadlinerLimit() {
        return headlinerLimit;
    }

    public LocalDate getConfirmationDate() {
        return confirmationDate;
    }

    public SelectionCriteria getCriteria() {
        return criteria;
    }

    public SchedulingType getSchedulingType() {
        return schedulingType;
    }

    public MoneyAmount getHeadlinerBudget() {
        return headlinerBudget;
    }
}
