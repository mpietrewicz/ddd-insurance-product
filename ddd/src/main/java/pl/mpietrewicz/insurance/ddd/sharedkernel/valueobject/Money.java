package pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import pl.mpietrewicz.insurance.ddd.annotations.domain.ValueObject;

import java.math.BigDecimal;
import java.util.Objects;

@ValueObject
@Embeddable
public class Money {

    @Column(nullable = false)
    private BigDecimal amount;

    protected Money() {}

    public Money(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money must be zero or positive");
        }
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        return new Money(this.amount.subtract(other.amount).max(BigDecimal.ZERO));
    }

    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean isGreaterThanZero() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount.stripTrailingZeros());
    }

    @Override
    public String toString() {
        return amount.toString();
    }

}
