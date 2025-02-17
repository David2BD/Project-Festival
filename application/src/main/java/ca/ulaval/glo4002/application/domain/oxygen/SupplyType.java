package ca.ulaval.glo4002.application.domain.oxygen;

public enum SupplyType {
    BOUGHT("bought"),
    PRODUCED("produced"),
    INVENTORY_USED("inventory-used");

    private final String displayName;

    SupplyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
