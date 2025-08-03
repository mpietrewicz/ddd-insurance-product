package pl.mpietrewicz.insurance.product.webapi.dto.converter;

import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;
import pl.mpietrewicz.insurance.product.webapi.dto.request.ApplicantDataRequest;

public interface ApplicantDataConverter {

    ApplicantData convert(ApplicantDataRequest applicantDataRequest);

}
