package ca.ulaval.glo4002.application.domain.scheduleSimulation.scheduling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SchedulingTypeTest {

    @Test
    void givenValidSchedulingType_whenFromString_thenReturnsEnumValue() {
        assertEquals(SchedulingType.CRESCENDO, SchedulingType.fromString("CRESCENDO"));
        assertEquals(SchedulingType.ROLLERCOASTER, SchedulingType.fromString("ROLLERCOASTER"));
    }

    @Test
    void givenValidSchedulingTypeIgnoringCase_whenFromString_thenReturnsEnumValue() {
        assertEquals(SchedulingType.CRESCENDO, SchedulingType.fromString("crescendo"));
        assertEquals(SchedulingType.ROLLERCOASTER, SchedulingType.fromString("rollercoaster"));
    }

    @Test
    void givenValidSchedulingTypeWithExtraSpaces_whenFromString_thenReturnsEnumValue() {
        assertEquals(SchedulingType.CRESCENDO, SchedulingType.fromString("  CRESCENDO  "));
        assertEquals(SchedulingType.ROLLERCOASTER, SchedulingType.fromString("  rollercoaster  "));
    }

    @Test
    void givenNullSchedulingType_whenFromString_thenThrowsIllegalArgumentException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> SchedulingType.fromString(null));
        assertEquals("Scheduling type cannot be null or blank", exception.getMessage());
    }

    @Test
    void givenBlankSchedulingType_whenFromString_thenThrowsIllegalArgumentException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> SchedulingType.fromString("   "));
        assertEquals("Scheduling type cannot be null or blank", exception.getMessage());
    }

    @Test
    void givenInvalidSchedulingType_whenFromString_thenThrowsIllegalArgumentException() {
        String invalidType = "INVALID_TYPE";
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> SchedulingType.fromString(invalidType));
        assertEquals("Invalid scheduling type: " + invalidType, exception.getMessage());
    }
}
