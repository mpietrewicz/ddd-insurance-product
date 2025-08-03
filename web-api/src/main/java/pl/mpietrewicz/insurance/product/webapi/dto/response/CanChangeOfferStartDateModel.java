package pl.mpietrewicz.insurance.product.webapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
@Schema(description = "Answer if offer strat date can by changed.")
public class CanChangeOfferStartDateModel extends RepresentationModel<CanChangeOfferStartDateModel> {

    private boolean canChange;

}