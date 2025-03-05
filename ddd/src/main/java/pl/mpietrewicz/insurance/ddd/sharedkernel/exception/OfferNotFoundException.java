package pl.mpietrewicz.insurance.ddd.sharedkernel.exception;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;

import java.util.NoSuchElementException;

public class OfferNotFoundException extends NoSuchElementException {

    public OfferNotFoundException(OfferId offerId) {
        super("Offer not found for id: " + offerId);
    }

}
