package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.reports.ProfitReport;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ProfitReportResponseDTO;

import java.math.BigDecimal;

public class ProfitReportMapper {
    public static ProfitReportResponseDTO toProfitReportResponseDTO(ProfitReport profitReport) {
        BigDecimal income = profitReport.income().twoDecimals();
        BigDecimal expenses = profitReport.expenses().twoDecimals();
        BigDecimal profits = profitReport.profit().twoDecimals();

        return new ProfitReportResponseDTO(income, expenses, profits);
    }
}
