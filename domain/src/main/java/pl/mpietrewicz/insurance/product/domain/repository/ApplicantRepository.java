package pl.mpietrewicz.insurance.product.domain.repository;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepository;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.product.domain.snapshot.Applicant;

import java.util.Optional;

@DomainRepository
public interface ApplicantRepository {

    Optional<Applicant> find(ApplicantId applicantId);

    void save(Applicant applicant);

}