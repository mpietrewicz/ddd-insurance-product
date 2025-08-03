package pl.mpietrewicz.insurance.product.domain.agregate.offer;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainFactory;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;

import java.time.LocalDate;

@DomainFactory
public class OfferFactory {

    public Offer create(ApplicantId applicantId, LocalDate startDate) {
        OfferId offerId = OfferId.generate();
        return new Offer(offerId, applicantId, startDate);
    }

}
