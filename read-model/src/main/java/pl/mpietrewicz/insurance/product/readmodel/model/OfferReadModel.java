package pl.mpietrewicz.insurance.product.readmodel.model;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;

import java.time.LocalDate;
import java.util.List;

public interface OfferReadModel {

    OfferId getAggregateId();

    ApplicantId getApplicantId();

    LocalDate getStartDate();

    List<OfferingReadModel> getOfferings();

}
