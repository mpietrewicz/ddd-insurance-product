package pl.mpietrewicz.insurance.product.domain.snapshot;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.Snapshot;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.SnapshotEntity;

import java.time.LocalDate;

@Snapshot
@Getter
@Entity
@NoArgsConstructor
public class Applicant extends SnapshotEntity {

    @EmbeddedId
    @AttributeOverride(name = "aggregateId", column = @Column(name = "applicantId", nullable = false))
    private ApplicantId applicantId;

    private LocalDate birthDate;

    private boolean chronicDiseases;

    private String occupation;

    public Applicant(ApplicantId applicantId, LocalDate birthDate, boolean chronicDiseases, String occupation) {
        this.applicantId = applicantId;
        this.birthDate = birthDate;
        this.chronicDiseases = chronicDiseases;
        this.occupation = occupation;
    }

}
