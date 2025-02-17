package ca.ulaval.glo4002.application.domain.pass.categories;

import ca.ulaval.glo4002.application.domain.pass.exceptions.InvalidFormatException;

public enum PassCategoryTypes {
    VIP("VIP"),
    PREMIUM("Premium"),
    STANDARD("Standard");

    private final String name;

    PassCategoryTypes(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static PassCategoryTypes fromString(String categoryName) {
        for (PassCategoryTypes type : values()) {
            if (type.name.equals(categoryName)) {
                return type;
            }
        }
        throw new InvalidFormatException();
    }
}
