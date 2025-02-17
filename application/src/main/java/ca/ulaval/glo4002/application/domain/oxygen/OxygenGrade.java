package ca.ulaval.glo4002.application.domain.oxygen;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum OxygenGrade {
    A(7500.00f, 5, 20, SupplyType.PRODUCED),
    B(8000.00f, 4, 10, SupplyType.PRODUCED),
    E(5000.00f, 1, 0, SupplyType.BOUGHT),
    ;

    private final float cost;
    private final int tanksPerBundle;
    private final int productionTime;
    private final SupplyType supplyType;

    OxygenGrade(float cost, int tanksPerBundle, int productionTime, SupplyType supplyType) {
        this.cost = cost;
        this.tanksPerBundle = tanksPerBundle;
        this.productionTime = productionTime;
        this.supplyType = supplyType;
    }

    public int getTanksPerBundle() {
        return tanksPerBundle;
    }

    public int getProductionTime() {
        return productionTime;
    }

    public SupplyType getSupplyType() {
        return supplyType;
    }

    public List<OxygenGrade> getGradesSortedFromBaseToHighest() {
        return Arrays.stream(OxygenGrade.values()).filter(grade -> grade.ordinal() >= this.ordinal()).sorted()
                .collect(Collectors.toList());
    }

    public static List<OxygenGrade> listGradesFromTo(OxygenGrade baseGrade, OxygenGrade topGrade) {
        int start = Math.min(baseGrade.ordinal(), topGrade.ordinal());
        int end = Math.max(baseGrade.ordinal(), topGrade.ordinal());

        return Arrays.stream(OxygenGrade.values()).filter(grade -> grade.ordinal() >= start && grade.ordinal() <= end)
                .collect(Collectors.toList());
    }

    private static Boolean canBeProduced(OxygenGrade grade, int daysBeforeFestival) {
        return daysBeforeFestival >= grade.getProductionTime();
    }

    public static OxygenGrade determineSupplyGrade(
            OxygenGrade baseGrade, int daysBeforeFestival
    ) {
        List<OxygenGrade> gradesFromCurrentToHighest = baseGrade.getGradesSortedFromBaseToHighest();

        for (OxygenGrade grade : gradesFromCurrentToHighest) {
            if (grade == OxygenGrade.A && OxygenGrade.canBeProduced(grade, daysBeforeFestival)) {
                return OxygenGrade.A;
            }
            else if (grade == OxygenGrade.B && OxygenGrade.canBeProduced(grade, daysBeforeFestival)) {
                return OxygenGrade.B;
            }
            else if (grade == OxygenGrade.E) {
                return OxygenGrade.E;
            }
        }

        throw new java.lang.IllegalArgumentException("Unable to determine an appropriate oxygen grade to supply.");
    }

    public LocalDateTime determineProductionTimestamp(LocalDateTime preparationOrderDate) {
        return DateUtils.calculateTimestamp(preparationOrderDate, productionTime);
    }

    private MoneyAmount getUnitCost() {
        return new MoneyAmount(cost / tanksPerBundle);
    }

    public MoneyAmount calculateCost(int quantity) {
        MoneyAmount unitCost = getUnitCost();
        return unitCost.multiply(quantity);
    }

}

