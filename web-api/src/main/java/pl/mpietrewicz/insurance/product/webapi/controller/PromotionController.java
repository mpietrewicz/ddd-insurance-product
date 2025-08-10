package pl.mpietrewicz.insurance.product.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domainapi.PromotionApplicationService;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingKey;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;
import pl.mpietrewicz.insurance.product.domainapi.exception.ProductNotAvailableException;
import pl.mpietrewicz.insurance.product.webapi.service.adapter.PromotionServiceAdapter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/offers/{offerId}/offerings/{offeringId}/promotions")
@RequiredArgsConstructor
@Tag(name = "Offering promotions", description = "Managing promotions for a specific offerings")
public class PromotionController {

    private final PromotionServiceAdapter promotionServiceAdapter;

    private final PromotionApplicationService promotionApplicationService;

    @Operation(summary = "Get available offerings for a specific insured",
            description = "Retrieves a list of available offerings for a specific insured based on eligibility.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available offerings retrieved successfully")
    })
    @GetMapping("/available")
    public CollectionModel<PromotionType> getAvailablePromotions(@PathVariable OfferId offerId,
                                                                 @PathVariable Long offeringId) {
        OfferingKey offeringKey = OfferingKey.of(offerId, offeringId);
        CollectionModel<PromotionType> availablePromotions = promotionServiceAdapter.getAvailablePromotions(offeringKey);

        if (!availablePromotions.getContent().isEmpty()) {
            for (PromotionType promotionType : availablePromotions.getContent()) {
                availablePromotions.add(getLinkToAddPromotion(offerId, offeringId, promotionType));
            }
        }
        return availablePromotions;
    }

    @Operation(summary = "Add a new offering",
            description = "Adds a new offering to the specified offer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Offering added successfully"),
            @ApiResponse(responseCode = "404", description = "Offer or product not found"),
            @ApiResponse(responseCode = "409", description = "Offering with requested product id cannot be added")
    })
    @PostMapping
    public ResponseEntity<RepresentationModel<?>> addPromotion(@PathVariable OfferId offerId,
                                                               @PathVariable Long offeringId,
                                                               @RequestParam PromotionType promotionType) {
        OfferingKey offeringKey = OfferingKey.of(offerId, offeringId);
        promotionApplicationService.addPromotion(promotionType, offeringKey);

        RepresentationModel<?> model = new RepresentationModel<>();
        return ResponseEntity.ok()
                .body(model);
    }

    @Operation(summary = "Delete an offering",
            description = "Deletes a specific offering from the offer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offering deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Offering not found")
    })
    @DeleteMapping
    public ResponseEntity<RepresentationModel<?>> removePromotion(@PathVariable OfferId offerId,
                                                                  @PathVariable Long offeringId,
                                                                  @RequestParam PromotionType promotionType) {
        OfferingKey offeringKey = OfferingKey.of(offerId, offeringId);
        promotionApplicationService.removePromotion(promotionType, offeringKey);

        RepresentationModel<?> model = new RepresentationModel<>();
        return ResponseEntity.ok()
                .body(model);
    }

    @ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<Void> handleNotAvailable(ProductNotAvailableException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    private static Link getLinkToAddPromotion(OfferId offerId, Long offeringId, PromotionType promotionType) {
        return linkTo(methodOn(PromotionController.class)
                .addPromotion(offerId, offeringId, promotionType)).withRel("add-promotion");
    }

}
