package ca.ulaval.glo4002.application.interfaces.validators;

import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.ProgramConfirmRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.application.interfaces.rest.validators.ProgramRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProgramRequestValidatorTest {
    private static final String ANY_STRING = "anyString";
    private static final String EMPTY_STRING = "";
    private static final float ANY_HEADLINER_BUDGET = 2000000;
    private static final int ANY_HEADLINER_LIMIT = 3;
    private ProgramRequestValidator programRequestValidator;

    @BeforeEach
    void setUp() {
        programRequestValidator = new ProgramRequestValidator();
    }

    @Test
    void givenProgramConfirmRequestWithAllFieldsPresent_whenValidate_thenDoesNotThrowException() {
        ProgramConfirmRequestDTO validProgramConfirmRequest =
                new ProgramConfirmRequestDTO(ANY_STRING, ANY_STRING, ANY_STRING, ANY_HEADLINER_BUDGET,
                                             ANY_HEADLINER_LIMIT);

        assertDoesNotThrow(() -> programRequestValidator.validate(validProgramConfirmRequest));
    }

    @Test
    void givenNullConfirmationDateInProgramConfirmRequest_whenValidate_thenThrowException() {
        ProgramConfirmRequestDTO programConfirmRequest =
                new ProgramConfirmRequestDTO(null, ANY_STRING, ANY_STRING, ANY_HEADLINER_BUDGET, ANY_HEADLINER_LIMIT);

        assertThrows(InvalidFormatException.class, () -> programRequestValidator.validate(programConfirmRequest));
    }

    @Test
    void givenNullCriteriaInProgramConfirmRequest_whenValidate_thenThrowException() {
        ProgramConfirmRequestDTO programConfirmRequest =
                new ProgramConfirmRequestDTO(ANY_STRING, null, ANY_STRING, ANY_HEADLINER_BUDGET, ANY_HEADLINER_LIMIT);

        assertThrows(InvalidFormatException.class, () -> programRequestValidator.validate(programConfirmRequest));
    }

    @Test
    void givenNullSchedulingTypeInProgramConfirmRequest_whenValidate_thenThrowException() {
        ProgramConfirmRequestDTO programConfirmRequest =
                new ProgramConfirmRequestDTO(ANY_STRING, ANY_STRING, null, ANY_HEADLINER_BUDGET, ANY_HEADLINER_LIMIT);

        assertThrows(InvalidFormatException.class, () -> programRequestValidator.validate(programConfirmRequest));
    }

    @Test
    void givenEmptyConfirmationDateInProgramConfirmRequest_whenValidate_thenThrowException() {
        ProgramConfirmRequestDTO programConfirmRequest =
                new ProgramConfirmRequestDTO(EMPTY_STRING, ANY_STRING, ANY_STRING, ANY_HEADLINER_BUDGET,
                                             ANY_HEADLINER_LIMIT);

        assertThrows(InvalidFormatException.class, () -> programRequestValidator.validate(programConfirmRequest));
    }

    @Test
    void givenEmptyCriteriaInProgramConfirmRequest_whenValidate_thenThrowException() {
        ProgramConfirmRequestDTO programConfirmRequest =
                new ProgramConfirmRequestDTO(ANY_STRING, EMPTY_STRING, ANY_STRING, ANY_HEADLINER_BUDGET,
                                             ANY_HEADLINER_LIMIT);

        assertThrows(InvalidFormatException.class, () -> programRequestValidator.validate(programConfirmRequest));
    }

    @Test
    void givenEmptySchedulingTypeInProgramConfirmRequest_whenValidate_thenThrowException() {
        ProgramConfirmRequestDTO programConfirmRequest =
                new ProgramConfirmRequestDTO(ANY_STRING, ANY_STRING, EMPTY_STRING, ANY_HEADLINER_BUDGET,
                                             ANY_HEADLINER_LIMIT);

        assertThrows(InvalidFormatException.class, () -> programRequestValidator.validate(programConfirmRequest));
    }

}
