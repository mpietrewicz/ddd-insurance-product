package pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject;

import pl.mpietrewicz.insurance.ddd.annotations.domain.ValueObject;
import pl.mpietrewicz.insurance.ddd.sharedkernel.converter.YearMonthConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.YearMonth;

@Embeddable
@ValueObject
public class AccrualMonth implements Serializable {

    @Convert(converter = YearMonthConverter.class)
    private YearMonth yearMonth;

    private AccrualMonth() {
    }

    private AccrualMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public static AccrualMonth with(YearMonth yearMonth) {
        return new AccrualMonth(yearMonth);
    }

    public static AccrualMonth withString(String yearMonthString) {
        return new AccrualMonth(YearMonth.parse(yearMonthString));
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

}