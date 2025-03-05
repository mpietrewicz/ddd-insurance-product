package pl.mpietrewicz.insurance.product.webapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Request to create a new offer for an insured person.")
public class CreateOfferRequest {

    @NotNull(message = "Offer start date cannot be null")
    @Schema(description = "Start date of the offer.", example = "2025-06-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private final LocalDate startDate;

    @Valid
    @NotNull(message = "Applicant data cannot be null")
    @Schema(description = "Data of the applicant for whom the offer is being created", requiredMode = Schema.RequiredMode.REQUIRED)
    private final ApplicantDataRequest applicantData;

}