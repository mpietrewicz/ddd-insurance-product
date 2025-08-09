package pl.mpietrewicz.insurance.product.domain.service.offering.impl;

import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.dto.OfferingContext;
import pl.mpietrewicz.insurance.product.domain.service.offering.OfferingContextFactory;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

@Service
public class OfferingContextFactoryImpl implements OfferingContextFactory {

    @Override
    public OfferingContext create(Offer offer, Product product, PromotionType promotionType) {
        Premium premium = product.getPremium();
        return new OfferingContext(product.getProductId(), premium, promotionType, product, offer);
    }

}
