package pl.mpietrewicz.insurance.product.webapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
@Schema(description = "Represents an premium schedule for the offered product.")
public class PremiumScheduleModel extends RepresentationModel<PremiumScheduleModel> {

    @Schema(description = "Months of scheduled payment")
    private CollectionModel<MonthlyPremiumModel> monthlyPremiums;

}