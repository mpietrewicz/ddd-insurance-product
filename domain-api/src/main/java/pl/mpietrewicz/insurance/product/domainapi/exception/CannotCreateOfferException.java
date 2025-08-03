package pl.mpietrewicz.insurance.product.domainapi.exception;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;

public class CannotCreateOfferException extends IllegalArgumentException {

    public CannotCreateOfferException(ApplicantId applicantId) {
        super("Cannot create offer for applicant id: " + applicantId);
    }

}
