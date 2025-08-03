package pl.mpietrewicz.insurance.product.domain.service.aplicant.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.product.domain.repository.ApplicantRepository;
import pl.mpietrewicz.insurance.product.domain.service.aplicant.ApplicantConverter;
import pl.mpietrewicz.insurance.product.domain.service.aplicant.ApplicantDataProvider;
import pl.mpietrewicz.insurance.product.domain.snapshot.Applicant;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicantDataProviderImpl implements ApplicantDataProvider {

    private final ApplicantRepository applicantRepository;

    private final ApplicantConverter applicantConverter;

    @Override
    public ApplicantData get(ApplicantId applicantId) {
        Optional<Applicant> applicant = applicantRepository.find(applicantId);
        if (applicant.isPresent()) {
            return applicantConverter.convert(applicant.get());
        } else {
            throw new IllegalStateException("Applicant with ID " + applicantId + " not found.");
        }
    }

}
