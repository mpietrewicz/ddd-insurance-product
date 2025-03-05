package pl.mpietrewicz.insurance.product.webapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Schema(description = "Request object for changing offer start date.")
public class ChangeStartDateRequest {

    @NotNull(message = "Offering start date cannot be null")
    @Schema(description = "New offer start date.", example = "2025-02-01")
    private final LocalDate startDate;

}