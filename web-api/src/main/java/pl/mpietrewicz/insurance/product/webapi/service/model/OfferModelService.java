package pl.mpietrewicz.insurance.product.webapi.service.model;

import org.springframework.hateoas.CollectionModel;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.OfferNotFoundException;
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferModel;

public interface OfferModelService {

    /**
     * Retrieves all offers associated with a given applicant ID.
     *
     * @param applicantId the unique identifier of the insurance applicant, must not be null
     * @return a CollectionModel containing all OfferModel objects associated with the applicant ID, never null
     * @throws IllegalArgumentException if applicantId is null
     */
    CollectionModel<OfferModel> getByApplicantId(ApplicantId applicantId);

    /**
     * Retrieves an OfferModel based on its unique identifier.
     *
     * @param offerId the unique identifier of the offer, must not be null
     * @return the OfferModel corresponding to the provided identifier, never null
     * @throws OfferNotFoundException   if no offer exists with the given identifier
     * @throws IllegalArgumentException if offerId is null
     */
    OfferModel getBy(OfferId offerId);

}
