package ca.ulaval.glo4002.application.domain.reports;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.application.domain.oxygen.SupplyType;

import java.time.LocalDateTime;
import java.util.UUID;

public class OxygenLogEntry {
    private final SupplyType supplyType;
    private final OxygenGrade oxygenGrade;
    private final int quantity;
    private final LocalDateTime timestamp;
    private final UUID id;

    public OxygenLogEntry(SupplyType supplyType, OxygenGrade oxygenGrade, int quantity, LocalDateTime timestamp) {
        this.supplyType = supplyType;
        this.oxygenGrade = oxygenGrade;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.id = UUID.randomUUID();
    }

    public SupplyType getSupplyType() {
        return supplyType;
    }

    public UUID getId() {
        return id;
    }

    public OxygenGrade getOxygenGrade() {
        return oxygenGrade;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public MoneyAmount calculateEntryCost() {
        return switch (supplyType) {
            case BOUGHT, PRODUCED -> oxygenGrade.calculateCost(quantity);
            case INVENTORY_USED -> new MoneyAmount(0);
        };
    }
}
