package pl.mpietrewicz.insurance.product.webapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Builder
@Schema(description = "Represents an available offer start date.")
public class AvailableStartDateModel extends RepresentationModel<AvailableStartDateModel> {

    @Schema(description = "The available start date for the offer.", example = "2023-10-01")
    private LocalDate startDate;

}