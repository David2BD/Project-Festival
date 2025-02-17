package ca.ulaval.glo4002.application.domain.pass;

import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;

import java.util.List;

public class PassOxygenSelector {

    public List<OxygenGrade> listCompatibleOxygenGrades(
            OxygenGrade baseGrade, int daysBeforeFestival
    ) {
        OxygenGrade supplyGrade = defineSupplyGrade(baseGrade, daysBeforeFestival);
        return OxygenGrade.listGradesFromTo(baseGrade, supplyGrade);
    }

    public OxygenGrade defineSupplyGrade(OxygenGrade baseGrade, int daysBeforeFestival) {
        return OxygenGrade.determineSupplyGrade(baseGrade, daysBeforeFestival);
    }
}
