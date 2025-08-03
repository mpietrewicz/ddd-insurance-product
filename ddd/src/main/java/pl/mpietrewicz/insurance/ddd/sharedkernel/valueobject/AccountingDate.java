package pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.mpietrewicz.insurance.ddd.annotations.domain.ValueObject;

import java.time.LocalDate;
import java.time.YearMonth;

@ValueObject
@Embeddable
@EqualsAndHashCode
@ToString
public class AccountingDate {

    @Column(nullable = false)
    private LocalDate date;

    protected AccountingDate() {}

    protected AccountingDate(LocalDate date) {
        this.date = date;
    }

    public static AccountingDate valueOf(LocalDate date) {
        return new AccountingDate(date);
    }

    public boolean isAfter(LocalDate date) {
        return this.date.isAfter(date);
    }

    public boolean isAfterOrEquals(LocalDate date) {
        return this.date.isAfter(date) || this.date.isEqual(date);
    }

    public boolean isBefore(LocalDate date) {
        return this.date.isBefore(date);
    }

    public boolean isBeforeOrEquals(LocalDate date) {
        return this.date.isBefore(date) || this.date.isEqual(date);
    }

    public YearMonth getNextMonth() {
        return YearMonth.from(date).plusMonths(1);
    }

}
