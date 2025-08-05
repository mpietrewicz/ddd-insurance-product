package pl.mpietrewicz.insurance.product.domain.service.offering;

import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.dto.OfferingContext;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

public interface OfferingContextFactory {

    OfferingContext create(Offer offer, Product product, PromotionType promotionType);

}
