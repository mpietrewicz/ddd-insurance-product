package pl.mpietrewicz.insurance.product.readmodel.model;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;

public interface ProductReadModel {

    ProductId getAggregateId();

    String getName();

    Premium getPremium();

}
