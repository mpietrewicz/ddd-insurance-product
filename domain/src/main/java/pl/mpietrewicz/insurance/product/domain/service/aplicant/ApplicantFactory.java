package pl.mpietrewicz.insurance.product.domain.service.aplicant;

import pl.mpietrewicz.insurance.product.domain.snapshot.Applicant;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

public interface ApplicantFactory {

    Applicant create(ApplicantData applicantData);

}
