package pl.mpietrewicz.insurance.product.webapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;

@Getter
@Builder
@Schema(description = "Represents a contract belonging to an insured individual.")
public class ContractModel extends RepresentationModel<ContractModel> {

    @Schema(description = "Unique identifier of the contract.")
    private ContractId id;

    @Schema(description = "Unique identifier of the insured individual.")
    private InsuredId insuredId;

}