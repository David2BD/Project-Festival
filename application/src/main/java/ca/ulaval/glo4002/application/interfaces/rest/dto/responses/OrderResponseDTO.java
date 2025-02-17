package ca.ulaval.glo4002.application.interfaces.rest.dto.responses;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(BigDecimal orderPrice, List<PassResponseDTO> passes) {}
