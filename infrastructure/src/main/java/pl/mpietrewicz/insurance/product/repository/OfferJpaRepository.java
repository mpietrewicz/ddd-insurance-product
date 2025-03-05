package pl.mpietrewicz.insurance.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepositoryImpl;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;

import java.util.List;

@DomainRepositoryImpl
public interface OfferJpaRepository extends JpaRepository<Offer, OfferId> {

    List<Offer> findByApplicantId(ApplicantId applicantId);

}
