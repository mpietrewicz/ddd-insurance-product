package pl.mpietrewicz.insurance.product.readmodel.model;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingId;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

public interface OfferingReadModel {

    OfferingId getId();
    
    ProductId getProductId();

    PromotionType getPromotionType();

}
