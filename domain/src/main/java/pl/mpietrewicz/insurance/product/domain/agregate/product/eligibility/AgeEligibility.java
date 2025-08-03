package pl.mpietrewicz.insurance.product.domain.agregate.product.eligibility;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Eligibility;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

import java.time.LocalDate;
import java.time.Period;

@Entity
@DiscriminatorValue("AGE")
@NoArgsConstructor
public class AgeEligibility extends Eligibility {

    private int minAge;

    private int maxAge;

    public AgeEligibility(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    @Override
    public boolean isSatisfiedBy(ApplicantData applicantData) {
        LocalDate birthDate = applicantData.getBirthDate();
        LocalDate now = LocalDate.now();
        int personAge = Period.between(birthDate, now).getYears();

        return personAge >= minAge && personAge <= maxAge;
    }

}
