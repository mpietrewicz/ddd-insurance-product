package pl.mpietrewicz.insurance.product.webapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Represents an insurance product")
public class ProductModel extends RepresentationModel<ProductModel> {

    @Schema(description = "Unique product ID", example = "00692374-3632-4c9d-b0e0-349cedfcd821")
    private ProductId id;

    @Schema(description = "Name of the product", example = "Product P")
    private String name;

    @Schema(description = "Monthly premium for the product", example = "10.50")
    private Premium premium;

    // todo: add Eligibility details

}