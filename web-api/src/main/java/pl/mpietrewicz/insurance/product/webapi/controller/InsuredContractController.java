package pl.mpietrewicz.insurance.product.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.product.webapi.dto.response.ContractModel;
import pl.mpietrewicz.insurance.product.webapi.service.model.ContractModelService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/insured/{insuredId}/contracts")
@RequiredArgsConstructor
@Tag(name = "Insured Contracts", description = "Get contracts for the insured")
public class InsuredContractController {

    private final ContractModelService contractModelService;

    @Operation(
            summary = "Get all contracts of the insured",
            description = "Returns a list of all contracts associated with a specific insured. "
                    + "Each contract contains a HATEOAS link to its detailed representation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of contracts retrieved successfully",
                    content = @Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = ContractModel.class))
            ),
            @ApiResponse(responseCode = "404", description = "Insured not found")
    })
    @GetMapping
    public CollectionModel<ContractModel> getContracts(@PathVariable InsuredId insuredId) {
        CollectionModel<ContractModel> contractsModel = contractModelService.getByInsuredId(insuredId);

        contractsModel.forEach(contract ->
                contract.add(ContractController.getLinkToGetContract(contract.getId())));
        return contractsModel;
    }

    public static Link getLinkToGetContracts(InsuredId insuredId) {
        return linkTo(methodOn(InsuredContractController.class)
                .getContracts(insuredId)).withSelfRel();
    }

}
