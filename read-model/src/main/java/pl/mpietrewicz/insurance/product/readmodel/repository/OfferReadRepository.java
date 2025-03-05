package pl.mpietrewicz.insurance.product.readmodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mpietrewicz.insurance.ddd.annotations.application.Reader;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.readmodel.model.OfferReadModel;

import java.util.List;
import java.util.Optional;

@Reader
public interface OfferReadRepository extends JpaRepository<Offer, Long> {

    Optional<OfferReadModel> findByAggregateId(OfferId offerId);

    List<OfferReadModel> findByApplicantId(ApplicantId applicantId);

    boolean existsByApplicantId(ApplicantId applicantId);

}
