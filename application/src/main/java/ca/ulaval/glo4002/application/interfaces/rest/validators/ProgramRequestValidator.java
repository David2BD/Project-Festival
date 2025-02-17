package ca.ulaval.glo4002.application.interfaces.rest.validators;

import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.ProgramConfirmRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.exceptions.InvalidFormatException;

public class ProgramRequestValidator {

    public void validate(ProgramConfirmRequestDTO programConfirmRequestDTO) {
        validateDate(programConfirmRequestDTO.confirmationDate());
        validateCriteria(programConfirmRequestDTO.criteria());
        validateSchedulingType(programConfirmRequestDTO.scheduling());
    }

    private void validateDate(String date) {
        if (date == null || date.isEmpty()) {
            throw new InvalidFormatException("invalid format");
        }
    }

    private void validateCriteria(String criteria) {
        if (criteria == null || criteria.isEmpty()) {
            throw new InvalidFormatException("invalid format");
        }
    }

    private void validateSchedulingType(String schedulingType) {
        if (schedulingType == null || schedulingType.isEmpty()) {
            throw new InvalidFormatException("invalid format");
        }
    }
}
