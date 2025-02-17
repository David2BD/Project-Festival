package ca.ulaval.glo4002.application.domain.oxygen;

import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.pass.PassOxygenSelector;
import ca.ulaval.glo4002.application.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public abstract class OxygenRequester {
    private final PassOxygenSelector passOxygenSelector;
    private LocalDateTime requestDate;

    protected OxygenRequester() {
        passOxygenSelector = new PassOxygenSelector();
    }

    protected OxygenRequester(PassOxygenSelector passOxygenSelector) {
        this.passOxygenSelector = passOxygenSelector;
    }

    public abstract OxygenGrade getBaseOxygenGrade();

    public abstract int calculateTanksNeeded(FestivalDates festivalDates);

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public List<OxygenGrade> listCompatibleOxygenGrades(LocalDate deadlineDate) {
        int daysBeforePreparationDeadline = calculateDaysToPrepareOxygen(deadlineDate);
        return passOxygenSelector.listCompatibleOxygenGrades(getBaseOxygenGrade(), daysBeforePreparationDeadline);
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public OxygenGrade findGradeToProduce(LocalDate deadlineDate) {
        int daysBeforePreparationDeadline = calculateDaysToPrepareOxygen(deadlineDate);
        return passOxygenSelector.defineSupplyGrade(getBaseOxygenGrade(), daysBeforePreparationDeadline);
    }

    private int calculateDaysToPrepareOxygen(LocalDate deadlineDate) {
        return DateUtils.numberOfDaysBetween(requestDate.toLocalDate(), deadlineDate) + 1;
    }
}
