package pl.mpietrewicz.insurance.product.webapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;

import java.time.LocalDate;

@Getter
@Builder
@Schema(description = "Represents an offer made to an insurance applicant, " +
        "including associated offerings and the offer's start date.")
public class OfferModel extends RepresentationModel<OfferModel> {

    @Schema(description = "The unique identifier for the offer.")
    private OfferId id;

    @Schema(description = "The ID of the insurance applicant to whom the offer is made.")
    private ApplicantId applicantId;

    @Schema(description = "The start date of the offer.", example = "2025-01-01")
    private LocalDate startDate;

    @Schema(description = "The offerings associated with this offer.")
    private CollectionModel<OfferingModel> offerings;

}