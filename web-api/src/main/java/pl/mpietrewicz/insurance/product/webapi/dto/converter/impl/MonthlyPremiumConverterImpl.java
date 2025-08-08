package pl.mpietrewicz.insurance.product.webapi.dto.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.product.domainapi.dto.MonthlyPremium;
import pl.mpietrewicz.insurance.product.webapi.dto.converter.MonthlyPremiumConverter;
import pl.mpietrewicz.insurance.product.webapi.dto.response.MonthlyPremiumModel;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MonthlyPremiumConverterImpl implements MonthlyPremiumConverter {

    @Override
    public MonthlyPremiumModel convert(MonthlyPremium monthlyPremium) {
        return MonthlyPremiumModel.builder()
                .month(monthlyPremium.getMonth())
                .premium(monthlyPremium.getPremium())
                .build();
    }

    @Override
    public CollectionModel<MonthlyPremiumModel> convert(List<MonthlyPremium> monthlyPremiums) {
        List<MonthlyPremiumModel> monthlyPremiumModels = monthlyPremiums.stream()
                .map(this::convert)
                .toList();

        return CollectionModel.of(monthlyPremiumModels);
    }

}
