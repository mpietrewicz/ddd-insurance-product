package pl.mpietrewicz.insurance.product.webapi.dto.converter.impl;

import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.InsuredRequirements;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.ProductData;
import pl.mpietrewicz.insurance.product.webapi.dto.converter.ProductDataConverter;
import pl.mpietrewicz.insurance.product.webapi.dto.request.CreateProductRequest;

@Component
public class ProductDataConverterImpl implements ProductDataConverter {

    @Override
    public ProductData convert(CreateProductRequest createProductRequest) {
        int maxEntryAge = createProductRequest.getMaxEntryAge();
        boolean forHealthyOnly = createProductRequest.isForHealthyOnly();
        InsuredRequirements insuredRequirements = new InsuredRequirements(maxEntryAge, forHealthyOnly);

        return ProductData.builder()
                .name(createProductRequest.getName())
                .premium(Premium.valueOf(createProductRequest.getPremium()))
                .validFrom(createProductRequest.getValidFrom())
                .validTo(createProductRequest.getValidTo())
                .insuredRequirements(insuredRequirements)
                .promotionTypes(createProductRequest.getPromotionTypes())
                .build();
    }

}
