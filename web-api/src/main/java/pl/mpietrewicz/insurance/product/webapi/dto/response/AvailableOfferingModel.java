package pl.mpietrewicz.insurance.product.webapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.List;

@Getter
@Builder
@Schema(description = "Represents an available offering with various product and promotional options.")
public class AvailableOfferingModel extends RepresentationModel<AvailableOfferingModel> {

    @Schema(description = "The ID of the product associated with this offering.")
    private ProductId productId;

    @Schema(description = "List of available promotion types for the offering",
            example = "[NO_PROMOTION, SINGLE_PROMOTION]")
    private List<PromotionType> availablePromotions;

}