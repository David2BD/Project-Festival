package ca.ulaval.glo4002.application.domain.pass;

import java.util.UUID;

public class PassNumber {
    private final String number;

    public PassNumber(long number) {
        this.number = String.valueOf(number);
    }

    public PassNumber(String passString) {
        this.number = passString;
    }

    public static PassNumber generateNewPassNumber() {
        long uuid = Math.abs(UUID.randomUUID().getMostSignificantBits());
        return new PassNumber(uuid);
    }

    public String getNumber() {
        return number;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (! (obj instanceof PassNumber other)) return false;
        return number == other.number;
    }

}
