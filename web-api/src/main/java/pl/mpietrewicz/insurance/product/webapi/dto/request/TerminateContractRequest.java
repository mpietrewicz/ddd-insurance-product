package pl.mpietrewicz.insurance.product.webapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Schema(description = "Request to terminate a contract.")
public class TerminateContractRequest {

    @NotNull(message = "Contract termination date cannot be null")
    @Schema(description = "Date when the contract should be terminated.", requiredMode = Schema.RequiredMode.REQUIRED)
    private final LocalDate terminationDate;

}