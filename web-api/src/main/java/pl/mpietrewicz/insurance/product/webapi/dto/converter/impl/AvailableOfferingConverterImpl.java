package pl.mpietrewicz.insurance.product.webapi.dto.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;
import pl.mpietrewicz.insurance.product.domainapi.dto.PremiumSchedule;
import pl.mpietrewicz.insurance.product.webapi.dto.converter.AvailableOfferingConverter;
import pl.mpietrewicz.insurance.product.webapi.dto.converter.MonthlyPremiumConverter;
import pl.mpietrewicz.insurance.product.webapi.dto.response.AvailableOfferingModel;
import pl.mpietrewicz.insurance.product.webapi.dto.response.PremiumScheduleModel;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AvailableOfferingConverterImpl implements AvailableOfferingConverter {

    @Override
    public AvailableOfferingModel convert(AvailableOffering availableOffering) {
        return AvailableOfferingModel.builder()
                .productId(availableOffering.getProductId())
                .premium(availableOffering.getPremium())
                .build();
    }

    @Override
    public CollectionModel<AvailableOfferingModel> convert(List<AvailableOffering> availableOfferings) {
        List<AvailableOfferingModel> availableOfferingModels = availableOfferings.stream()
                .map(this::convert)
                .toList();

        return CollectionModel.of(availableOfferingModels);
    }

}
