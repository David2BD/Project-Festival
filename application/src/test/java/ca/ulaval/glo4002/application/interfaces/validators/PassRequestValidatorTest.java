package ca.ulaval.glo4002.application.interfaces.validators;

import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.PassRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.application.interfaces.rest.validators.PassRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PassRequestValidatorTest {
    private final long ANY_NUMBER = 1;
    private final String ANY_VALID_PASS_CATEGORY = "Standard";
    private final String ANY_VALID_PASS_OPTION = "passe_journaliere";
    private final String ANY_VALID_EVENT_DATE = "2060-07-17";
    private static final String INVALID_EVENT_DATE = "invalid-date";
    private static final String NULL_EVENT_DATE = null;

    List<PassRequestDTO> passes;
    PassRequestValidator passRequestValidator = new PassRequestValidator();

    @BeforeEach
    void setUp() {
        passes = new ArrayList<>();
    }

    @Test
    void givenValidPassRequest_whenValidate_thenDoesNotThrowException() {
        PassRequestDTO validRequest =
                new PassRequestDTO(ANY_NUMBER, ANY_VALID_PASS_CATEGORY, ANY_VALID_PASS_OPTION, ANY_VALID_EVENT_DATE);
        passes.add(validRequest);

        assertDoesNotThrow(() -> passRequestValidator.validate(passes));
    }

    @Test
    void givenMissingCategoryInPassRequest_whenValidate_thenThrowException() {
        PassRequestDTO requestMissingCategory =
                new PassRequestDTO(ANY_NUMBER, null, ANY_VALID_PASS_OPTION, ANY_VALID_EVENT_DATE);
        passes.add(requestMissingCategory);

        assertThrows(InvalidFormatException.class, () -> passRequestValidator.validate(passes));
    }

    @Test
    void givenEmptyCategoryInPassRequest_whenValidate_thenThrowException() {
        PassRequestDTO emptyCategoryRequest =
                new PassRequestDTO(ANY_NUMBER, "", ANY_VALID_PASS_OPTION, ANY_VALID_EVENT_DATE);
        passes.add(emptyCategoryRequest);

        assertThrows(InvalidFormatException.class, () -> passRequestValidator.validate(passes));
    }

    @Test
    void givenMissingOptionInPassRequest_whenValidate_thenThrowException() {
        PassRequestDTO requestMissingOption =
                new PassRequestDTO(ANY_NUMBER, ANY_VALID_PASS_CATEGORY, null, ANY_VALID_EVENT_DATE);
        passes.add(requestMissingOption);

        assertThrows(InvalidFormatException.class, () -> passRequestValidator.validate(passes));
    }

    @Test
    void givenEmptyOptionInPassRequest_whenValidate_thenThrowException() {
        PassRequestDTO emptyOptionRequest =
                new PassRequestDTO(ANY_NUMBER, ANY_VALID_PASS_CATEGORY, "", ANY_VALID_EVENT_DATE);
        passes.add(emptyOptionRequest);

        assertThrows(InvalidFormatException.class, () -> passRequestValidator.validate(passes));
    }

    @Test
    void givenValidEventDateInPassRequest_whenValidate_thenDoesNotThrowException() {
        PassRequestDTO validRequest =
                new PassRequestDTO(ANY_NUMBER, ANY_VALID_PASS_CATEGORY, ANY_VALID_PASS_OPTION, ANY_VALID_EVENT_DATE);
        passes.add(validRequest);

        assertDoesNotThrow(() -> passRequestValidator.validate(passes));
    }

    @Test
    void givenInvalidEventDateInPassRequest_whenValidate_thenThrowsException() {
        PassRequestDTO invalidDateRequest =
                new PassRequestDTO(ANY_NUMBER, ANY_VALID_PASS_CATEGORY, ANY_VALID_PASS_OPTION, INVALID_EVENT_DATE);
        passes.add(invalidDateRequest);

        assertThrows(InvalidFormatException.class, () -> passRequestValidator.validate(passes));
    }

    @Test
    void givenNullEventDateInPassRequest_whenValidate_thenDoesNotThrowException() {
        PassRequestDTO nullDateRequest =
                new PassRequestDTO(ANY_NUMBER, ANY_VALID_PASS_CATEGORY, ANY_VALID_PASS_OPTION, NULL_EVENT_DATE);
        passes.add(nullDateRequest);

        assertDoesNotThrow(() -> passRequestValidator.validate(passes));
    }

}
