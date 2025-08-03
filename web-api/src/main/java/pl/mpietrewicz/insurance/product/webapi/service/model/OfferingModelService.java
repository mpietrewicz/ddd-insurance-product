package pl.mpietrewicz.insurance.product.webapi.service.model;

import org.springframework.hateoas.CollectionModel;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.OfferNotFoundException;
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferingModel;

public interface OfferingModelService {

    /**
     * Retrieves a collection of OfferingModel objects associated with the specified offer ID.
     *
     * @param offerId the unique identifier of the offer, must not be null
     * @return a CollectionModel containing all OfferingModel objects related to the provided offer ID, never null
     * @throws OfferNotFoundException if no offering exists with the given identifier
     * @throws IllegalArgumentException if offerId is null
     */
    CollectionModel<OfferingModel> getBy(OfferId offerId);

    /**
     * Retrieves an OfferingModel based on its unique offer ID and offering ID.
     *
     * @param offerId the unique identifier of the offer, must not be null
     * @param offeringId the unique identifier of the offering, must not be null
     * @return the OfferingModel corresponding to the provided offer ID and offering ID, never null
     * @throws OfferNotFoundException if no offering exists with the given offer ID and offering ID
     * @throws IllegalArgumentException if offerId or offeringId is null
     */
    OfferingModel getBy(OfferId offerId, Long offeringId);

}
