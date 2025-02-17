package ca.ulaval.glo4002.application.domain.oxygen;

import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.reports.OxygenLogEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static java.lang.Math.min;

public class OxygenProvider {
    private final Map<OxygenGrade, Integer> inventory = new HashMap<>();
    private TreeSet<OxygenLogEntry> oxygenLogEntries;
    OxygenTankProducer oxygenTankProducer;

    public OxygenProvider() {
        oxygenTankProducer = new OxygenTankProducer();
    }

    public void supplyOxygen(List<OxygenRequester> oxygenRequesters, FestivalDates festivalDates) {
        initializeInventory();
        initializeLogs();

        sortByChronologicalOrder(oxygenRequesters);
        for (OxygenRequester oxygenRequester : oxygenRequesters) {
            supplyOxygenForRequester(oxygenRequester, festivalDates);
        }
    }

    private void sortByChronologicalOrder(List<OxygenRequester> oxygenRequesters) {
        oxygenRequesters.sort(Comparator.comparing(OxygenRequester::getRequestDate).reversed());
    }

    private void supplyOxygenForRequester(
            OxygenRequester oxygenRequester, FestivalDates festivalDates
    ) {
        int tanksNeeded = oxygenRequester.calculateTanksNeeded(festivalDates);
        int tanksRetrieved = supplyFromInventory(oxygenRequester, festivalDates);

        if (tanksRetrieved < tanksNeeded) {
            int tanksToSupply = tanksNeeded - tanksRetrieved;
            supplyFromProducer(oxygenRequester, tanksToSupply, festivalDates.getStartDate());
        }
    }

    private int supplyFromInventory(
            OxygenRequester oxygenRequester, FestivalDates festivalDates
    ) {
        List<OxygenGrade> oxygenGrades = oxygenRequester.listCompatibleOxygenGrades(festivalDates.getStartDate());
        int tanksNeeded = oxygenRequester.calculateTanksNeeded(festivalDates);
        int totalTanksRemoved = 0;

        for (OxygenGrade oxygenGrade : oxygenGrades) {
            if (getCountByType(oxygenGrade) > 0) {
                int tanksRemoved = removeOxygenByGrade(oxygenGrade, tanksNeeded - totalTanksRemoved,
                                                       oxygenRequester.getRequestDate());
                totalTanksRemoved += tanksRemoved;
            }
            if (totalTanksRemoved >= tanksNeeded) {
                return totalTanksRemoved;
            }
        }
        return totalTanksRemoved;
    }

    private void supplyFromProducer(OxygenRequester oxygenRequester, int tanksToProduce, LocalDate festivalStartDate) {
        OxygenGrade gradeToProduce = oxygenRequester.findGradeToProduce(festivalStartDate);
        int producedTanks = oxygenTankProducer.calculateNumberOfTanksProduced(tanksToProduce, gradeToProduce);
        LocalDateTime productionTimestamp =
                gradeToProduce.determineProductionTimestamp(oxygenRequester.getRequestDate());

        addOxygen(gradeToProduce, producedTanks, productionTimestamp);

        removeOxygenByGrade(gradeToProduce, tanksToProduce, productionTimestamp);
    }

    public TreeSet<OxygenLogEntry> getOxygenLogEntries() {
        return oxygenLogEntries;
    }

    public Map<OxygenGrade, Integer> getOxygenInventory() {
        return inventory;
    }

    private void initializeInventory() {
        for (OxygenGrade type : OxygenGrade.values()) {
            inventory.put(type, 0);
        }
    }

    private void initializeLogs() {
        oxygenLogEntries =
                new TreeSet<>(Comparator.comparing(OxygenLogEntry::getTimestamp).thenComparing(OxygenLogEntry::getId));
    }

    private void addOxygen(OxygenGrade supplyGrade, int suppliedTanks, LocalDateTime timestamp) {
        oxygenLogEntries.add(new OxygenLogEntry(supplyGrade.getSupplyType(), supplyGrade, suppliedTanks, timestamp));
        inventory.put(supplyGrade, inventory.get(supplyGrade) + suppliedTanks);
    }

    private int removeOxygenByGrade(OxygenGrade oxygenGrade, int tanksRequested, LocalDateTime timestamp) {
        int tanksInInventory = getCountByType(oxygenGrade);
        int tanksRetrieved = min(tanksRequested, tanksInInventory);

        oxygenLogEntries.add(new OxygenLogEntry(SupplyType.INVENTORY_USED, oxygenGrade, tanksRetrieved, timestamp));
        inventory.put(oxygenGrade, tanksInInventory - tanksRetrieved);

        return tanksRetrieved;
    }

    private int getCountByType(OxygenGrade type) {
        return inventory.get(type);
    }
}
