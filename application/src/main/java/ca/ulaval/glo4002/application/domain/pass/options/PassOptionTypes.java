package ca.ulaval.glo4002.application.domain.pass.options;

import ca.ulaval.glo4002.application.domain.pass.exceptions.InvalidFormatException;

public enum PassOptionTypes {
    EVENT("forfait"),
    DAILY("passe_journaliere");

    private final String name;

    PassOptionTypes(String name) {
        this.name = name;
    }

    public static PassOptionTypes fromString(String optionName) {
        for (PassOptionTypes type : values()) {
            if (type.name.equals(optionName)) {
                return type;
            }
        }
        throw new InvalidFormatException();
    }

    public String toString() {
        return name;
    }
}
