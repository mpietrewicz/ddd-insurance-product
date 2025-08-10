package pl.mpietrewicz.insurance.product.domainapi.dto.offering;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import pl.mpietrewicz.insurance.ddd.annotations.domain.PublishedLanguage;

import java.util.Objects;
import java.util.UUID;

@PublishedLanguage
@Embeddable
public class OfferingId {

    private String id;

    protected OfferingId() {
    }

    public OfferingId(String id) {
        this.id = Objects.requireNonNull(id, "offeringId");
    }

    @JsonValue
    public String getId() {
        return id;
    }

    public static OfferingId generate(){
        return new OfferingId(UUID.randomUUID().toString());
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
