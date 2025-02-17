package ca.ulaval.glo4002.application.interfaces.rest.validators;

import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.OrderRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.PassRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.application.utils.DateUtils;
import jakarta.inject.Inject;

import java.util.List;

public class OrderRequestValidator {
    PassRequestValidator passRequestValidator;

    @Inject
    public OrderRequestValidator(PassRequestValidator passRequestValidator) {
        this.passRequestValidator = passRequestValidator;
    }

    public void validate(OrderRequestDTO orderRequest) {
        validateOrderDate(orderRequest.orderDate());
        validateVendorCode(orderRequest.vendorCode());
        validatePasses(orderRequest.passes());
        validateOrderDateFormat(orderRequest.orderDate());
    }

    private void validateOrderDate(String orderDate) {
        if (orderDate == null || orderDate.isEmpty()) {
            throw new InvalidFormatException("invalid format");
        }
    }

    private void validateVendorCode(String vendorCode) {
        if (vendorCode == null || vendorCode.isEmpty()) {
            throw new InvalidFormatException("invalid format");
        }
    }

    private void validatePasses(List<PassRequestDTO> passRequestDTO) {
        if (passRequestDTO == null || passRequestDTO.isEmpty()) {
            throw new InvalidFormatException("invalid format");
        }
        passRequestValidator.validate(passRequestDTO);
    }

    private void validateOrderDateFormat(String orderDate) {
        if (! DateUtils.isValidISODateTime(orderDate)) {
            throw new InvalidFormatException("invalid format");
        }
    }

}

