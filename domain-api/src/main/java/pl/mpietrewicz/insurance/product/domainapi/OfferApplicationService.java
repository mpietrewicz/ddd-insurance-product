package pl.mpietrewicz.insurance.product.domainapi;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domainapi.exception.CannotAcceptOfferException;
import pl.mpietrewicz.insurance.product.domainapi.exception.CannotCreateOfferException;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.OfferNotFoundException;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

import java.time.LocalDate;
import java.util.List;

public interface OfferApplicationService {

    /**
     * Checks whether an insurance offer can be created based on the provided data.
     *
     * @param applicantData the applicant's data used to evaluate offer possibility.
     * @return {@code true} if an offer can be created, {@code false} otherwise.
     */
    boolean canCreateOffer(ApplicantData applicantData);

    /**
     * Creates a new insurance offer based on the applicant's data.
     *
     * @param applicantData the applicant's data.
     * @param startDate     the desired start date of the insurance coverage.
     * @return the identifier of the newly created offer.
     * @throws CannotCreateOfferException if an offer cannot be created for the given data (e.g., due to unmet conditions).
     */
    OfferId createOffer(ApplicantData applicantData, LocalDate startDate);

    /**
     * Deletes an existing offer.
     * <p>
     * If an offer with the given identifier does not exist, this operation has no effect and does not throw an error.
     *
     * @param offerId the identifier of the offer to delete.
     */
    void deleteOffer(OfferId offerId);

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

    /**
     * Retrieves a list of available start dates for the given offer.
     * The returned start dates represent the possible coverage start dates
     * associated with the specified offer.
     *
     * @param offerId the identifier of the offer for which available start dates are to be retrieved
     * @return a list of available start dates as {@code LocalDate} objects
     */
    List<LocalDate> getAvailableStartDates(OfferId offerId);

    /**
     * Changes the start date of an existing insurance offer.
     *
     * @param offerId   the identifier of the offer for which the start date is to be changed
     * @param startDate the new start date for the offer
     */
    void changeStartDate(OfferId offerId, LocalDate startDate);

}