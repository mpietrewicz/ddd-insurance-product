package pl.mpietrewicz.insurance.product.domain.repository;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepository;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;

import java.util.List;
import java.util.Optional;

@DomainRepository
public interface OfferRepository {

    Optional<Offer> load(OfferId offerId);

    void save(Offer offer);

    void delete(OfferId offerId);

    List<Offer> getAllOffersFor(ApplicantId applicantId);

}