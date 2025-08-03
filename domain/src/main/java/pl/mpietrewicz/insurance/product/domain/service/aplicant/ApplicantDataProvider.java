package pl.mpietrewicz.insurance.product.domain.service.aplicant;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

public interface ApplicantDataProvider {

    ApplicantData get(ApplicantId applicantId);

}
