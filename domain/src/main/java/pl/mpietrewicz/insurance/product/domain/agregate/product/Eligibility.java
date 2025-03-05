package pl.mpietrewicz.insurance.product.domain.agregate.product;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.BaseEntity;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "eligibility_type")
public abstract class Eligibility extends BaseEntity {

    public abstract boolean isSatisfiedBy(ApplicantData applicantData);

}
