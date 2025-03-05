package pl.mpietrewicz.insurance.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepositoryImpl;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.product.domain.snapshot.Applicant;

@DomainRepositoryImpl
public interface ApplicantJpaRepository extends JpaRepository<Applicant, ApplicantId> {
}
