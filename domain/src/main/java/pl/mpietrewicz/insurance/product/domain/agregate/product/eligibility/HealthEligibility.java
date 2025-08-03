package pl.mpietrewicz.insurance.product.domain.agregate.product.eligibility;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Eligibility;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

@Entity
@DiscriminatorValue("HEALTH")
@NoArgsConstructor
public class HealthEligibility extends Eligibility {

    private boolean forHealthyOnly;

    public HealthEligibility(boolean forHealthyOnly) {
        this.forHealthyOnly = forHealthyOnly;
    }

    @Override
    public boolean isSatisfiedBy(ApplicantData applicantData) {
        return !forHealthyOnly || !applicantData.isChronicDiseases();
    }

}
