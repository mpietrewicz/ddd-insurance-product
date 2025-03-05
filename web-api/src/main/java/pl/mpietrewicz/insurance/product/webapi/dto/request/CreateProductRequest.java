package pl.mpietrewicz.insurance.product.webapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for creating a new insurance product.")
public class CreateProductRequest {

    @NotBlank(message = "Product mame cannot be blank")
    @Schema(description = "Name of the product.", example = "Product Health")
    private String name;

    @NotNull(message = "Product premium cannot be null")
    @Schema(description = "Monthly premium amount for the product.", example = "10.50")
    private BigDecimal premium;

    @NotNull(message = "Product valid from date cannot be null")
    @Schema(description = "Date from which the product is valid.", example = "2020-01-01")
    private LocalDate validFrom;

    @NotNull(message = "Product valid to date cannot be null")
    @Schema(description = "Date to which the product is valid.", example = "2029-12-31")
    private LocalDate validTo;

    @NotNull(message = "Product max entry age cannot be null")
    @Min(value = 0, message = "Product max entry age cannot be negative")
    @Max(value = 150, message = "Product max entry age seems unrealistically high")
    @Schema(description = "Maximum entry age for an insured person to be eligible for the product.", example = "78")
    private Integer maxEntryAge;

    @Schema(description = "Indicates if the product is available only for healthy insured persons.", example = "true")
    private boolean forHealthyOnly;

    @Schema(description = "Type of promotion allowed for this product.", example = "NO_PROMOTION")
    private PromotionType promotionType;

}