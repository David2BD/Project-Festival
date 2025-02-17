package ca.ulaval.glo4002.application.domain.reports;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;

import java.util.Map;
import java.util.TreeSet;

public record OxygenReport(Map<OxygenGrade, Integer> inventory, TreeSet<OxygenLogEntry> oxygenLogEntries) {

    public MoneyAmount calculateOxygenCost() {
        MoneyAmount totalCost = new MoneyAmount(0);
        for (OxygenLogEntry oxygenLogEntry : oxygenLogEntries) {
            MoneyAmount entryCost = oxygenLogEntry.calculateEntryCost();
            totalCost = totalCost.add(entryCost);
        }

        return totalCost;
    }
}
