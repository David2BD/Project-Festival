package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.reports.ProfitReport;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.ProfitReportResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfitReportMapperTest {
    private static final MoneyAmount ANY_PROFIT_AMOUNT = new MoneyAmount(10000.19f);
    private static final MoneyAmount ANY_INCOME_AMOUNT = new MoneyAmount(15000.19f);
    private static final MoneyAmount ANY_EXPENSES_AMOUNT = new MoneyAmount(5000.00f);
    @Mock
    private ProfitReport profitReportMock;

    @BeforeEach
    void setUp() {
        when(profitReportMock.income()).thenReturn(ANY_INCOME_AMOUNT);
        when(profitReportMock.expenses()).thenReturn(ANY_EXPENSES_AMOUNT);
        when(profitReportMock.profit()).thenReturn(ANY_PROFIT_AMOUNT);
    }

    @Test
    void givenValidProfitReport_whenMapped_thenReturnProfitReportResponseDTOWithSameAttributes() {
        BigDecimal expectedIncome = ANY_INCOME_AMOUNT.twoDecimals();
        BigDecimal expectedExpenses = ANY_EXPENSES_AMOUNT.twoDecimals();
        BigDecimal expectedProfits = ANY_PROFIT_AMOUNT.twoDecimals();
        ProfitReportResponseDTO profitReportResponseDTO =
                ProfitReportMapper.toProfitReportResponseDTO(profitReportMock);

        assertEquals(expectedIncome, profitReportResponseDTO.income());
        assertEquals(expectedExpenses, profitReportResponseDTO.expenses());
        assertEquals(expectedProfits, profitReportResponseDTO.profit());
    }
}