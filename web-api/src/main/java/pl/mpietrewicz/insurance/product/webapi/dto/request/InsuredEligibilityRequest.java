package pl.mpietrewicz.insurance.product.webapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Schema(description = "Insured person's data used to determine eligibility for an insurance offer.")
public class InsuredEligibilityRequest {

    @NotNull(message = "Insured birth date cannot be null")
    @Schema(description = "Person's date of birth.", example = "1985-06-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private final LocalDate birthDate;

    @Schema(description = "Indicates whether the person has chronic diseases.", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    private final boolean chronicDiseases;

    @NotBlank(message = "Insured occupation cannot be blank")
    @Schema(description = "Person's occupation.", example = "police officer", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String occupation;

}