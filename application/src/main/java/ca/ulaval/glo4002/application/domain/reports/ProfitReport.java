package ca.ulaval.glo4002.application.domain.reports;

import ca.ulaval.glo4002.application.domain.MoneyAmount;

public record ProfitReport(MoneyAmount income, MoneyAmount expenses, MoneyAmount profit) {}
