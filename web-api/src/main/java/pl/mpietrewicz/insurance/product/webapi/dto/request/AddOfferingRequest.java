package pl.mpietrewicz.insurance.product.webapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

@Getter
@AllArgsConstructor
@Schema(description = "Request object for adding a new offering to an existing offer.")
public class AddOfferingRequest {

    @NotNull(message = "Offering product id cannot be null")
    @Schema(description = "ID of the product associated with the offering.")
    private final ProductId productId;

    @Schema(description = "Promotion type for the offering", example = "SINGLE_PROMOTION")
    private PromotionType promotionType;

}