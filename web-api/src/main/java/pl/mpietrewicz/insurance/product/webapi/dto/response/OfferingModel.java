package pl.mpietrewicz.insurance.product.webapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

@Getter
@Builder
@Schema(description = "Represents an offering associated with a product, " +
        "including its start date and promotional status.")
public class OfferingModel extends RepresentationModel<OfferingModel> {

    @Schema(description = "The unique identifier of the offering.")
    private Long id;

    @Schema(description = "The ID of the product associated with this offering.")
    private ProductId productId;

    @Schema(description = "Promotion type for the offering", example = "[NO_PROMOTION, SINGLE_PROMOTION]")
    private PromotionType promotion;

}
