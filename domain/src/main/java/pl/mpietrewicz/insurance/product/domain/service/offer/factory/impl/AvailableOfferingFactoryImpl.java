package pl.mpietrewicz.insurance.product.domain.service.offer.factory.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.service.offer.factory.AvailableOfferingFactory;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;

@DomainService
@RequiredArgsConstructor
public class AvailableOfferingFactoryImpl implements AvailableOfferingFactory {

    @Override
    public AvailableOffering create(Product product) {
        ProductId productId = product.getProductId();
        Premium premium = product.getPremium();

        return new AvailableOffering(productId, premium);
    }

}
