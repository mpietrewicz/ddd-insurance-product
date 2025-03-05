package pl.mpietrewicz.insurance.product.domain.service.aplicant.impl;

import pl.mpietrewicz.insurance.ddd.annotations.domain.SnapshotFactory;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.product.domain.service.aplicant.ApplicantFactory;
import pl.mpietrewicz.insurance.product.domain.snapshot.Applicant;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

import java.time.LocalDate;

@SnapshotFactory
public class ApplicantFactoryImpl implements ApplicantFactory {

    @Override
    public Applicant create(ApplicantData applicantData) {
        ApplicantId applicantId = applicantData.getApplicantId();
        LocalDate birthDate = applicantData.getBirthDate();
        boolean chronicDiseases = applicantData.isChronicDiseases();
        String occupation = applicantData.getOccupation();

        return new Applicant(applicantId, birthDate, chronicDiseases, occupation);
    }

}
