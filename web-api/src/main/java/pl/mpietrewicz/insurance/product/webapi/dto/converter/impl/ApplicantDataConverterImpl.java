package pl.mpietrewicz.insurance.product.webapi.dto.converter.impl;

import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;
import pl.mpietrewicz.insurance.product.webapi.dto.converter.ApplicantDataConverter;
import pl.mpietrewicz.insurance.product.webapi.dto.request.ApplicantDataRequest;

import java.time.LocalDate;

@Component
public class ApplicantDataConverterImpl implements ApplicantDataConverter {

    @Override
    public ApplicantData convert(ApplicantDataRequest applicantDataRequest) {
        ApplicantId applicantId = applicantDataRequest.getApplicantId();
        LocalDate birthDate = applicantDataRequest.getBirthDate();
        boolean chronicDiseases = applicantDataRequest.isChronicDiseases();
        String occupation = applicantDataRequest.getOccupation();

        return new ApplicantData(applicantId, birthDate, chronicDiseases, occupation);
    }

}
