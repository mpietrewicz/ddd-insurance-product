package pl.mpietrewicz.insurance.product.domain.service.offering.impl;

import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.dto.OfferingContext;
import pl.mpietrewicz.insurance.product.domain.service.offering.OfferingContextFactory;

@Service
public class OfferingContextFactoryImpl implements OfferingContextFactory {

    @Override
    public OfferingContext create(Offer offer, Product product, boolean promotion) {
        Premium premium = product.getPremium();
        return new OfferingContext(product.getProductId(), premium, promotion, offer);
    }

}
