package pl.mpietrewicz.insurance.product.domainapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;

import java.time.LocalDate;

@Getter
@Builder
@RequiredArgsConstructor
public class ApplicantData {

    private final ApplicantId applicantId;

    private final LocalDate birthDate;

    private final boolean chronicDiseases;

    private final String occupation;

}
