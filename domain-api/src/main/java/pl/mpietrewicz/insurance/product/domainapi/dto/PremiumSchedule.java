package pl.mpietrewicz.insurance.product.domainapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PremiumSchedule {

    private final List<MonthlyPremium> monthlyPremiums;

}
