package pl.mpietrewicz.insurance.product.domainapi.offer;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;

import java.time.LocalDate;
import java.util.List;

public interface OfferSchedulingUseCase {

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
