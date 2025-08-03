package pl.mpietrewicz.insurance.product.repository.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepositoryImpl;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.product.domain.repository.ApplicantRepository;
import pl.mpietrewicz.insurance.product.domain.snapshot.Applicant;
import pl.mpietrewicz.insurance.product.repository.ApplicantJpaRepository;

import java.util.Optional;

@DomainRepositoryImpl
@RequiredArgsConstructor
public class ApplicantRepositoryImpl implements ApplicantRepository {

    private final ApplicantJpaRepository jpaRepository;

    @Override
    public Optional<Applicant> find(ApplicantId applicantId) {
        return jpaRepository.findById(applicantId);
    }

    @Override
    public void save(Applicant applicant) {
        jpaRepository.save(applicant);
    }

}
