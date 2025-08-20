package pl.mpietrewicz.insurance.product.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domainapi.exception.CannotAcceptOfferException;
import pl.mpietrewicz.insurance.product.domainapi.offer.OfferAcceptanceUseCase;
import pl.mpietrewicz.insurance.product.webapi.dto.response.CanAcceptOfferModel;

import static pl.mpietrewicz.insurance.product.webapi.controller.ContractController.getLinkToGetContract;

@RestController
@RequestMapping("/offers/{offerId}/accept")
@RequiredArgsConstructor
@Tag(name = "Offer actions", description = "Operations for managing offer acceptance and start date modifications")
public class OfferAcceptController {

    private final OfferAcceptanceUseCase offerAcceptanceUseCase;

    @Operation(summary = "Can accept offer",
            description = "Returns true if the offer can be accepted, false otherwise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check performed successfully"),
            @ApiResponse(responseCode = "404", description = "Offer not found")
    })
    @GetMapping
    public CanAcceptOfferModel canAcceptOffer(@PathVariable OfferId offerId) {
        boolean canAcceptOffer = offerAcceptanceUseCase.canAcceptOffer(offerId);

        return CanAcceptOfferModel.builder()
                .canAccept(canAcceptOffer)
                .build();
    }

    @Operation(summary = "Accept offer",
            description = "Accept a specific offer and create a corresponding contract insurance.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offer accepted successfully"),
            @ApiResponse(responseCode = "404", description = "Offer not found"),
            @ApiResponse(responseCode = "409", description = "Offer cannot be accepted due to business rules")
    })
    @PostMapping
    public ResponseEntity<RepresentationModel<?>> acceptOffer(@PathVariable OfferId offerId) {
        ContractId contractId = offerAcceptanceUseCase.acceptOffer(offerId);

        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(getLinkToGetContract(contractId).withRel("created-contract"));
        return ResponseEntity.ok()
                .body(model);
    }

    @ExceptionHandler(CannotAcceptOfferException.class)
    public ResponseEntity<Void> handleNotFound(CannotAcceptOfferException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
