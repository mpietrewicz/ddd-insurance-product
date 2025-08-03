package pl.mpietrewicz.insurance.product.domain.snapshot;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mpietrewicz.insurance.ddd.annotations.domain.Snapshot;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.SnapshotEntity;

@Snapshot
@Entity
@NoArgsConstructor
public class Accounting extends SnapshotEntity {

    @Id
    private Long id = 1L;

    @Getter
    @Setter
    @Embedded
    @Column(nullable = false)
    private AccountingDate accountingDate;

    public Accounting(AccountingDate accountingDate) {
        this.accountingDate = accountingDate;
    }

}
