package ca.ulaval.glo4002.application.interfaces.validators;

import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.OrderRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.PassRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.application.interfaces.rest.validators.OrderRequestValidator;
import ca.ulaval.glo4002.application.interfaces.rest.validators.PassRequestValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class OrderRequestValidatorTest {
    private final String ANY_VALID_ORDER_DATE = "2060-05-21T15:23:20.142Z";
    private final String ANY_VENDOR_CODE = "VALID_VENDOR";
    private static final long ANY_NUMBER = 1;
    private final PassRequestDTO ANY_PASS_REQUEST_DTO =
            new PassRequestDTO(ANY_NUMBER, "Standard", "passe_journaliere", "2060-07-17");
    private static final String INVALID_ORDER_DATE = "invalid-date";
    private static final String VALID_VENDOR_CODE = "VALID_VENDOR";

    private static final PassRequestDTO VALID_PASS_REQUEST_DTO =
            new PassRequestDTO(ANY_NUMBER, "Standard", "passe_journaliere", "2060-07-17");

    @Mock
    PassRequestValidator passRequestValidator;

    @InjectMocks
    OrderRequestValidator orderRequestValidator;

    @Test
    void givenValidOrderRequest_whenValidate_thenDoesNotThrowException() {
        OrderRequestDTO validOrderRequest =
                new OrderRequestDTO(ANY_VALID_ORDER_DATE, ANY_VENDOR_CODE, List.of(ANY_PASS_REQUEST_DTO));
        doNothing().when(passRequestValidator).validate(List.of(ANY_PASS_REQUEST_DTO));

        assertDoesNotThrow(() -> orderRequestValidator.validate(validOrderRequest));
    }

    @Test
    void givenMissingDateInOrderRequest_whenValidate_thenThrowException() {
        OrderRequestDTO invalidOrderRequest = new OrderRequestDTO(null, ANY_VENDOR_CODE, List.of(ANY_PASS_REQUEST_DTO));

        assertThrows(InvalidFormatException.class, () -> orderRequestValidator.validate(invalidOrderRequest));
    }

    @Test
    void givenMissingVendorCodeInOrderRequest_whenValidate_thenThrowException() {
        OrderRequestDTO invalidOrderRequest =
                new OrderRequestDTO(ANY_VALID_ORDER_DATE, null, List.of(ANY_PASS_REQUEST_DTO));

        assertThrows(InvalidFormatException.class, () -> orderRequestValidator.validate(invalidOrderRequest));
    }

    @Test
    void givenMissingPassListInOrderRequest_whenValidate_thenThrowException() {
        OrderRequestDTO invalidOrderRequest = new OrderRequestDTO(ANY_VALID_ORDER_DATE, ANY_VENDOR_CODE, List.of());

        assertThrows(InvalidFormatException.class, () -> orderRequestValidator.validate(invalidOrderRequest));
    }

    @Test
    void givenInvalidOrderDateFormat_whenValidate_thenThrowsException() {
        OrderRequestDTO invalidOrderRequest =
                new OrderRequestDTO(INVALID_ORDER_DATE, VALID_VENDOR_CODE, List.of(VALID_PASS_REQUEST_DTO));

        assertThrows(InvalidFormatException.class, () -> orderRequestValidator.validate(invalidOrderRequest));
    }

    @Test
    void givenNullOrderDate_whenValidate_thenThrowsException() {
        OrderRequestDTO invalidOrderRequest =
                new OrderRequestDTO(null, VALID_VENDOR_CODE, List.of(VALID_PASS_REQUEST_DTO));

        assertThrows(InvalidFormatException.class, () -> orderRequestValidator.validate(invalidOrderRequest));
    }

}