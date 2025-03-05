package pl.mpietrewicz.insurance.product.domainapi.exception;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;

public class ProductNotAvailableException extends IllegalStateException {

    public ProductNotAvailableException(OfferId offerId) {
        super("Product is not available for offer: " + offerId);
    }

}
