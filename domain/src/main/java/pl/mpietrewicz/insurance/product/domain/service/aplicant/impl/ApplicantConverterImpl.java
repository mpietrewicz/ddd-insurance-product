package pl.mpietrewicz.insurance.product.domain.service.aplicant.impl;

import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.product.domain.service.aplicant.ApplicantConverter;
import pl.mpietrewicz.insurance.product.domain.snapshot.Applicant;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

@Service
public class ApplicantConverterImpl implements ApplicantConverter {

    @Override
    public ApplicantData convert(Applicant applicant) {
        return new ApplicantData(
                applicant.getApplicantId(),
                applicant.getBirthDate(),
                applicant.isChronicDiseases(),
                applicant.getOccupation()
        );
    }

}
