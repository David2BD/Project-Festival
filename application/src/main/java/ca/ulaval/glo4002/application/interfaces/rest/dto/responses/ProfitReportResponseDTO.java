package ca.ulaval.glo4002.application.interfaces.rest.dto.responses;

import java.math.BigDecimal;

public record ProfitReportResponseDTO(BigDecimal income, BigDecimal expenses, BigDecimal profit) {}
