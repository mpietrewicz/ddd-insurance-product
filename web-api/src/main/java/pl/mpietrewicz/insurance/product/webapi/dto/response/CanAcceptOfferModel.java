package pl.mpietrewicz.insurance.product.webapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
@Schema(description = "Answer if offer can be accepted.")
public class CanAcceptOfferModel extends RepresentationModel<CanAcceptOfferModel> {

    private boolean canAccept;

}