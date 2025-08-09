package pl.mpietrewicz.insurance.product.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.product.domainapi.ContractApplicationService;
import pl.mpietrewicz.insurance.product.webapi.dto.response.ContractModel;
import pl.mpietrewicz.insurance.product.webapi.service.model.ContractModelService;
import pl.mpietrewicz.insurance.product.webapi.dto.request.TerminateContractRequest;

import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
@Tag(name = "Contracts", description = "Managing contracts")
public class ContractController {

    private final ContractModelService contractModelService;

    private final ContractApplicationService contractApplicationService;

    @Operation(summary = "Get a contract by ID",
            description = "Retrieves details of a specific contract by its unique ID. "
                    + "If the contract is eligible for termination, a HATEOAS link is added to allow termination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contract retrieved successfully",
                    content = @Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = ContractModel.class))),
            @ApiResponse(responseCode = "404", description = "Contract not found")
    })
    @GetMapping("/{contractId}")
    public ContractModel getContract(@PathVariable ContractId contractId) {
        ContractModel contractModel = contractModelService.getById(contractId);

        if (contractApplicationService.canTerminateContract(contractId)) {
            contractModel.add(getLinkToTerminateContract(contractId));
        }
        return contractModel;
    }

    @Operation(summary = "Terminate a contract",
            description = "Terminates an active contract by setting a termination details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contract terminated successfully"),
            @ApiResponse(responseCode = "404", description = "Contract not found")
    })
    @PatchMapping("/{contractId}")
    public ResponseEntity<RepresentationModel<?>> terminateContract(@PathVariable ContractId contractId,
                                            @Valid @RequestBody TerminateContractRequest terminateContractRequest) {
        LocalDate terminationDate = terminateContractRequest.getTerminationDate();
        contractApplicationService.terminateContract(contractId, terminationDate);

        return ResponseEntity.ok()
                .body(new RepresentationModel<>());
    }

    public static Link getLinkToGetContract(ContractId contractId) {
        return linkTo(methodOn(ContractController.class)
                .getContract(contractId)).withSelfRel();
    }

    private static Link getLinkToTerminateContract(ContractId contractId) {
        return linkTo(methodOn(ContractController.class)
                .terminateContract(contractId, null)).withRel("terminate-contract");
    }

}
