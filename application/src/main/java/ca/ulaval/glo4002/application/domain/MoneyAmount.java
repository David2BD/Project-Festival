package ca.ulaval.glo4002.application.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class MoneyAmount {
    private final BigDecimal amount;

    public MoneyAmount() {
        this.amount = BigDecimal.ZERO;
    }

    public MoneyAmount(BigDecimal amount) {

        this.amount = amount;
    }

    public MoneyAmount(int amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public MoneyAmount(float amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public MoneyAmount(double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public MoneyAmount(MoneyAmount amount) {
        this.amount = amount.getAmount();
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public BigDecimal twoDecimals() {
        return this.amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public MoneyAmount add(MoneyAmount other) {
        return new MoneyAmount(this.amount.add(other.amount));
    }

    public MoneyAmount subtract(MoneyAmount other) {
        return new MoneyAmount(this.amount.subtract(other.amount));
    }

    public MoneyAmount multiply(int other) {
        return new MoneyAmount(this.amount.multiply(BigDecimal.valueOf(other)));
    }

    public MoneyAmount multiply(long other) {
        return new MoneyAmount(this.amount.multiply(BigDecimal.valueOf(other)));
    }

    public MoneyAmount multiply(float other) {
        return new MoneyAmount(this.amount.multiply(BigDecimal.valueOf(other)));
    }

    public static MoneyAmount zero() {
        return new MoneyAmount(BigDecimal.ZERO);
    }

    public boolean isGreaterOrEqualTo(MoneyAmount moneyAmount) {
        return amount.compareTo(moneyAmount.amount) >= 0;
    }

    public int divide(int divisor) {
        return this.amount.divideToIntegralValue(BigDecimal.valueOf(divisor)).intValue();
    }

    public float floatValue() {
        return this.twoDecimals().floatValue();
    }

    public boolean equals(MoneyAmount other) {
        return this.amount.compareTo(other.amount) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyAmount that = (MoneyAmount) o;
        return amount.compareTo(that.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
