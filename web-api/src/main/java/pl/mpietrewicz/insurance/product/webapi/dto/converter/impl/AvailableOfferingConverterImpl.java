package pl.mpietrewicz.insurance.product.webapi.dto.converter.impl;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;
import pl.mpietrewicz.insurance.product.webapi.dto.converter.AvailableOfferingConverter;
import pl.mpietrewicz.insurance.product.webapi.dto.response.AvailableOfferingModel;

import java.util.List;

@Component
public class AvailableOfferingConverterImpl implements AvailableOfferingConverter {

    @Override
    public AvailableOfferingModel convert(AvailableOffering availableOffering) {
        return AvailableOfferingModel.builder()
                .productId(availableOffering.getProductId())
                .promotionOptions(availableOffering.getPromotionOptions())
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
