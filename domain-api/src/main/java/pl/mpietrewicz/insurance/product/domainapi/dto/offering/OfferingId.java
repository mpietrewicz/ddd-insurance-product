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
public class OfferingId implements Serializable {

    @NonNull
    private final OfferId offerId;

    @NonNull
    private final Long offeringId;

    @JsonCreator
    private OfferingId(
            @JsonProperty("offerId") OfferId offerId,
            @JsonProperty("offeringId") Long offeringId
    ) {
        if (offerId == null) throw new IllegalArgumentException("offerId cannot be null");
        if (offeringId == null || offeringId <= 0) throw new IllegalArgumentException("offeringId must be > 0");
        this.offerId = offerId;
        this.offeringId = offeringId;
    }

    public static OfferingId of(OfferId offerId, Long offeringId) {
        return new OfferingId(offerId, offeringId);
    }

}
