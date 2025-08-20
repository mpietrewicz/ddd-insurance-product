package pl.mpietrewicz.insurance.product.domainapi.offer;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;
import pl.mpietrewicz.insurance.product.domainapi.exception.CannotCreateOfferException;

import java.time.LocalDate;

public interface OfferCreationUseCase {

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

}
