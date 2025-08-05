package pl.mpietrewicz.insurance.product.domainapi;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.OfferNotFoundException;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.ProductNotFoundException;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;
import pl.mpietrewicz.insurance.product.domainapi.exception.ProductNotAvailableException;

import java.util.List;

public interface OfferingService {

    /**
     * Retrieves a list of available offerings for the specified offer.
     * An offering represents a product that can be associated with the given offer, along with
     * its available promotion options.
     *
     * @param offerId the identifier of the offer for which available offerings are to be retrieved
     * @return a list of available offerings associated with the specified offer
     */
    List<AvailableOffering> getAvailableOfferings(OfferId offerId);

    /**
     * Adds a new offering (i.e., product) to the specified offer.
     * This assumes the product is valid and available for the applicant.
     *
     * @param offerId       the identifier of the offer
     * @param productId     the identifier of the product to add
     * @param promotionType the promotion type for the offering
     * @return the internal ID of the added offering within the offer
     * @throws OfferNotFoundException       if the offer with the given ID does not exist
     * @throws ProductNotFoundException     if the product with the given ID does not exist
     * @throws ProductNotAvailableException if the product is unavailable for the offer due to business rules
     */
    Long addOffering(OfferId offerId, ProductId productId, PromotionType promotionType);

    /**
     * Removes a previously added offering from the specified offer.
     *
     * @param offerId    the identifier of the offer
     * @param offeringId the internal ID of the offering to be removed
     * @throws OfferNotFoundException if the offer with the given ID does not exist
     */
    void removeOffering(OfferId offerId, Long offeringId);

}
