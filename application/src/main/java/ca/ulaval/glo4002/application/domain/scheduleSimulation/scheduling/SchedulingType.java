package ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling;

public enum SchedulingType {
    CRESCENDO,
    ROLLERCOASTER;

    public static SchedulingType fromString(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Scheduling type cannot be null or blank");
        }
        try {
            return SchedulingType.valueOf(value.trim().toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid scheduling type: " + value);
        }
    }
}
