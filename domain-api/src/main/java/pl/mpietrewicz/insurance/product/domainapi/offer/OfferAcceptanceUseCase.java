package pl.mpietrewicz.insurance.product.domainapi.offer;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.OfferNotFoundException;
import pl.mpietrewicz.insurance.product.domainapi.exception.CannotAcceptOfferException;

public interface OfferAcceptanceUseCase {

    /**
     * Checks whether the specified offer can be accepted.
     *
     * @param offerId the identifier of the offer to check.
     * @return {@code true} if the offer can be accepted, {@code false} otherwise.
     * @throws OfferNotFoundException if no offer with the given ID is found.
     */
    boolean canAcceptOffer(OfferId offerId);

    /**
     * Accepts the specified offer, resulting in the creation of an insurance policy (contract).
     * <p>
     * Upon successful acceptance, a domain event is published to indicate that the applicant has become insured.
     *
     * @param offerId the identifier of the offer to accept.
     * @return the identifier of the newly created contract.
     * @throws OfferNotFoundException     if no offer with the given ID is found.
     * @throws CannotAcceptOfferException if the offer cannot be accepted (e.g., due to conditions not being met).
     */
    ContractId acceptOffer(OfferId offerId);

}
