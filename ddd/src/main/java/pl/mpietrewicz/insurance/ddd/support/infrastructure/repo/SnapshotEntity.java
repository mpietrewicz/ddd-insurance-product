package pl.mpietrewicz.insurance.ddd.support.infrastructure.repo;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

@MappedSuperclass
public abstract class SnapshotEntity {

    private Instant timestamp;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.timestamp = Instant.now();
    }

}
