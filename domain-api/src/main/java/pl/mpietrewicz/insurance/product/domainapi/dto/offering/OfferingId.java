package pl.mpietrewicz.insurance.product.domainapi.dto.offering;

import jakarta.persistence.Embeddable;
import pl.mpietrewicz.insurance.ddd.annotations.domain.PublishedLanguage;

import java.util.Objects;

@PublishedLanguage
@Embeddable
public class OfferingId {

    private Long id;

    protected OfferingId() {
    }

    public OfferingId(Long id) {
        this.id = Objects.requireNonNull(id, "offeringId");
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof OfferingId that && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
