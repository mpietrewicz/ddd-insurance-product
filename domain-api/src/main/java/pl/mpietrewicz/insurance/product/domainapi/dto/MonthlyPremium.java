package pl.mpietrewicz.insurance.product.domainapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;

import java.time.YearMonth;

@Getter
@RequiredArgsConstructor
public class MonthlyPremium {

    private final YearMonth month;

    private final Premium premium;

}
