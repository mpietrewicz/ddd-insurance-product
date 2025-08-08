package pl.mpietrewicz.insurance.product.webapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;

import java.time.YearMonth;

@Getter
@Builder
@Schema(description = "Represents exact month and premium amount for the offered product, including promotion.")
public class MonthlyPremiumModel extends RepresentationModel<MonthlyPremiumModel> {

    @Schema(description = "Month of premium")
    private YearMonth month;

    @Schema(description = "Amount of premium")
    private Premium premium;

}