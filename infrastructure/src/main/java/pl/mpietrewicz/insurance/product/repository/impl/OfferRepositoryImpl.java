package pl.mpietrewicz.insurance.product.repository.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepositoryImpl;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.GenericJpaRepository;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.repository.OfferRepository;
import pl.mpietrewicz.insurance.product.repository.OfferJpaRepository;

import java.util.List;

@DomainRepositoryImpl
@RequiredArgsConstructor
public class OfferRepositoryImpl extends GenericJpaRepository<Offer, OfferId> implements OfferRepository {

    private final OfferJpaRepository offerJpaRepository;

    @Override
    public List<Offer> getAllOffersFor(ApplicantId applicantId) {
        return offerJpaRepository.findByApplicantId(applicantId);
    }

}