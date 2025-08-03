package pl.mpietrewicz.insurance.product.readmodel.model;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;

public interface OfferingReadModel {

    Long getEntityId();
    
    ProductId getProductId();

    boolean isPromotion();

}
