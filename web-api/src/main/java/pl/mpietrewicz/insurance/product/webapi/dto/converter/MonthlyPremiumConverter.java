package pl.mpietrewicz.insurance.product.webapi.dto.converter;

import org.springframework.hateoas.CollectionModel;
import pl.mpietrewicz.insurance.product.domainapi.dto.MonthlyPremium;
import pl.mpietrewicz.insurance.product.webapi.dto.response.MonthlyPremiumModel;

import java.util.List;

public interface MonthlyPremiumConverter {

    MonthlyPremiumModel convert(MonthlyPremium monthlyPremium);

    CollectionModel<MonthlyPremiumModel> convert(List<MonthlyPremium> monthlyPremiums);

}
