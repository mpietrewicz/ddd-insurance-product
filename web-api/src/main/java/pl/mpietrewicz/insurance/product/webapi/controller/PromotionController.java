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

    private static final String REL_ADD_PROMOTION = "add-promotion";

    private final PromotionServiceAdapter promotionServiceAdapter;

    private final PromotionApplicationService promotionApplicationService;

    @Operation(summary = "Get available promotions for a specific offering",
            description = "Retrieves a list of available promotions for the specified offering based on promotion policy.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available promotions retrieved successfully")
    })
    @GetMapping("/available")
    public CollectionModel<PromotionType> getAvailablePromotions(@PathVariable OfferId offerId,
                                                                 @PathVariable Long offeringId) {
        OfferingKey offeringKey = toOfferingKey(offerId, offeringId);
        CollectionModel<PromotionType> availablePromotions = promotionServiceAdapter.getAvailablePromotions(offeringKey);

        if (!availablePromotions.getContent().isEmpty()) {
            availablePromotions.getContent()
                    .forEach(promotionType ->
                            availablePromotions.add(buildAddPromotionLink(offerId, offeringId, promotionType)));
        }
        return availablePromotions;
    }

    @Operation(summary = "Apply a promotion",
            description = "Applies the selected promotion to the specified offering.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Promotion applied successfully"),
            @ApiResponse(responseCode = "404", description = "Offer or product not found"),
            @ApiResponse(responseCode = "409", description = "Promotion cannot be applied")
    })
    @PostMapping
    public ResponseEntity<RepresentationModel<?>> applyPromotion(@PathVariable OfferId offerId,
                                                                 @PathVariable Long offeringId,
                                                                 @RequestParam PromotionType promotionType) {
        OfferingKey offeringKey = toOfferingKey(offerId, offeringId);
        promotionApplicationService.applyPromotion(promotionType, offeringKey);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RepresentationModel<>());
    }

    @Operation(summary = "Revoke a promotion",
            description = "Revokes the selected promotion from the specified offering.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promotion revoked successfully"),
            @ApiResponse(responseCode = "404", description = "Offering not found")
    })
    @DeleteMapping
    public ResponseEntity<RepresentationModel<?>> revokePromotion(@PathVariable OfferId offerId,
                                                                  @PathVariable Long offeringId,
                                                                  @RequestParam PromotionType promotionType) {
        OfferingKey offeringKey = toOfferingKey(offerId, offeringId);
        promotionApplicationService.revokePromotion(promotionType, offeringKey);

        return ResponseEntity.ok()
                .body(new RepresentationModel<>());
    }

    @ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<Void> handleNotAvailable(ProductNotAvailableException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    private static OfferingKey toOfferingKey(OfferId offerId, Long offeringId) {
        return OfferingKey.of(offerId, offeringId);
    }

    private static Link buildAddPromotionLink(OfferId offerId, Long offeringId, PromotionType promotionType) {
        return linkTo(methodOn(PromotionController.class)
                .applyPromotion(offerId, offeringId, promotionType)).withRel(REL_ADD_PROMOTION);
    }

}
