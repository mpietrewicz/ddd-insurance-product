package pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import pl.mpietrewicz.insurance.ddd.annotations.domain.ValueObject;

import java.math.BigDecimal;
import java.util.Objects;

@ValueObject
@Embeddable
public class Premium {

    public static final Premium ZERO = new Premium(BigDecimal.ZERO);
    public static final Premium TEN = new Premium(BigDecimal.TEN);

    @Column(nullable = false)
    private BigDecimal amount;

    protected Premium() {}

    protected Premium(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Premium must be zero or positive");
        }
        this.amount = amount;
    }

    public static Premium valueOf(BigDecimal bigDecimal) {
        return new Premium(bigDecimal);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Premium add(Premium other) {
        return new Premium(this.amount.add(other.amount));
    }

    public Premium subtract(Premium other) {
        return new Premium(this.amount.subtract(other.amount).max(BigDecimal.ZERO));
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
        if (!(o instanceof Premium)) return false;
        Premium premium = (Premium) o;
        return amount.compareTo(premium.amount) == 0;
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
