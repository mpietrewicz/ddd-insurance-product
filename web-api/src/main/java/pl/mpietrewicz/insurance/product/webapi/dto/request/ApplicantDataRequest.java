package pl.mpietrewicz.insurance.product.webapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Data provided by the insurance applicant.")
public class ApplicantDataRequest {

    @NotNull(message = "Applicant id cannot be null")
    @Schema(description = "The unique identifier of the applicant.")
    private final ApplicantId applicantId;

    @NotNull(message = "Applicant birth date cannot be null")
    @Schema(description = "Person's date of birth.", example = "1985-06-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private final LocalDate birthDate;

    @Schema(description = "Indicates whether the person has chronic diseases.", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    private final boolean chronicDiseases;

    @NotBlank(message = "Applicant occupation cannot be blank")
    @Schema(description = "Person's occupation.", example = "police officer", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String occupation;

}