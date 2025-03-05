package pl.mpietrewicz.insurance.product.domain.service.offer.policy.impl;

import org.springframework.context.annotation.Primary;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainPolicy;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.product.domain.service.offer.policy.OfferStartPolicy;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@DomainPolicy
public class ThreeFirstMonthsPolicy implements OfferStartPolicy {

    int ONE_MONTH = 1;
    int TWO_MONTHS = 2;
    int FIRST_DAY_OF_MONTH = 1;

    @Override
    public List<LocalDate> determine(AccountingDate accountingDate) {
        YearMonth nextMonth = accountingDate.getNextMonth();
        return Stream.of(nextMonth,
                        nextMonth.plusMonths(ONE_MONTH),
                        nextMonth.plusMonths(TWO_MONTHS))
                .map(month -> month.atDay(FIRST_DAY_OF_MONTH))
                .collect(Collectors.toList());
    }

}
