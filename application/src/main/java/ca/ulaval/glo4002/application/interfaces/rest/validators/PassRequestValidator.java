package ca.ulaval.glo4002.application.interfaces.rest.validators;

import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.PassRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.application.utils.DateUtils;

import java.util.List;

public class PassRequestValidator {

    public void validate(List<PassRequestDTO> passes) {
        for (PassRequestDTO passRequestDTO : passes) {
            validatePassCategory(passRequestDTO.passCategory());
            validatePassOption(passRequestDTO.passOption());
            validatePassDate(passRequestDTO.eventDate());
        }
    }

    private void validatePassCategory(String passCategory) {
        if (passCategory == null || passCategory.isEmpty()) {
            throw new InvalidFormatException("invalid format");
        }
    }

    private void validatePassOption(String passOption) {
        if (passOption == null || passOption.isEmpty()) {
            throw new InvalidFormatException("invalid format");
        }
    }

    private void validatePassDate(String passDate) {
        if (! DateUtils.isValidLocalDateOrNull(passDate)) {
            throw new InvalidFormatException("invalid format");
        }
    }

}
