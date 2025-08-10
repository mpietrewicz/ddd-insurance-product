package pl.mpietrewicz.insurance.product.domainapi.dto.offering;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@ToString
public class OfferingKey implements Serializable {

    @NonNull
    private final OfferId offerId;

    @NonNull
    private final OfferingId offeringId;

    @JsonCreator
    private OfferingKey(@JsonProperty("offerId") OfferId offerId, @JsonProperty("offeringId") OfferingId offeringId) {
        if (offerId == null) {
            throw new IllegalArgumentException("offerId cannot be null");
        }
        if (offeringId == null || offeringId.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("offeringId cannot be null or empty");
        }
        this.offerId = offerId;
        this.offeringId = offeringId;
    }

    public static OfferingKey of(OfferId offerId, OfferingId offeringId) {
        return new OfferingKey(offerId, offeringId);
    }

}
