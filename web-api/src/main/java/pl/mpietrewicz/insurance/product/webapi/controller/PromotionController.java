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
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingId;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingKey;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;
import pl.mpietrewicz.insurance.product.domainapi.exception.ProductNotAvailableException;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/offers/{offerId}/offerings/{offeringId}/promotions")
@RequiredArgsConstructor
@Tag(name = "Promotions", description = "Managing promotions for a specific offerings")
public class PromotionController {

    private static final String REL_APPLY_PROMOTION = "apply-promotion";
    private static final String REL_REVOKE_PROMOTION = "revoke-promotion";

    private final PromotionApplicationService promotionApplicationService;

    @Operation(summary = "Get available promotions for a specific offering",
            description = "Retrieves a list of available promotions for the specified offering based on promotion policy.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available promotions retrieved successfully")
    })
    @GetMapping
    public CollectionModel<PromotionType> getAvailablePromotions(@PathVariable OfferId offerId,
                                                                 @PathVariable OfferingId offeringId) {
        OfferingKey offeringKey = OfferingKey.of(offerId, offeringId);
        List<PromotionType> availablePromotions = promotionApplicationService.getAvailablePromotions(offeringKey);

        CollectionModel<PromotionType> promotionModel = CollectionModel.of(availablePromotions);
        addApplyPromotionLinks(promotionModel, availablePromotions, offeringKey);

        List<PromotionType> revocablePromotions = promotionApplicationService.listRevocablePromotions(offeringKey);
        addRevokePromotionLinks(promotionModel, revocablePromotions, offeringKey);

        return promotionModel;
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
                                                                 @PathVariable OfferingId offeringId,
                                                                 @RequestParam PromotionType promotionType) {
        OfferingKey offeringKey = OfferingKey.of(offerId, offeringId);
        promotionApplicationService.applyPromotion(promotionType, offeringKey);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RepresentationModel<>());
    }

    @Operation(summary = "Revoke a promotion",
            description = "Revokes the selected promotion from the specified offering.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Promotion revoked or already not applied"),
            @ApiResponse(responseCode = "404", description = "Offering not found")
    })
    @DeleteMapping
    public ResponseEntity<RepresentationModel<?>> revokePromotion(@PathVariable OfferId offerId,
                                                                  @PathVariable OfferingId offeringId,
                                                                  @RequestParam PromotionType promotionType) {
        OfferingKey offeringKey = OfferingKey.of(offerId, offeringId);
        promotionApplicationService.revokePromotion(promotionType, offeringKey);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<Void> handleNotAvailable(ProductNotAvailableException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    private void addApplyPromotionLinks(CollectionModel<PromotionType> promotionModel, 
                                        List<PromotionType> availablePromotions, OfferingKey offeringKey) {
        for (PromotionType promotionType : availablePromotions) {
            Link link = buildApplyPromotionLink(offeringKey, promotionType);
            promotionModel.add(link);
        }
    }

    private void addRevokePromotionLinks(CollectionModel<PromotionType> promotionModel,
                                         List<PromotionType> revocablePromotions, OfferingKey offeringKey) {
        for (PromotionType promotionType : revocablePromotions) {
            Link link = buildRevokePromotionLink(offeringKey, promotionType);
            promotionModel.add(link);
        }
    }

    private static Link buildApplyPromotionLink(OfferingKey offeringKey, PromotionType promotionType) {
        OfferId offerId = offeringKey.getOfferId();
        OfferingId offeringId = offeringKey.getOfferingId();

        return linkTo(methodOn(PromotionController.class)
                .applyPromotion(offerId, offeringId, promotionType))
                .withRel(REL_APPLY_PROMOTION);
    }

    private static Link buildRevokePromotionLink(OfferingKey offeringKey, PromotionType promotionType) {
        OfferId offerId = offeringKey.getOfferId();
        OfferingId offeringId = offeringKey.getOfferingId();

        return linkTo(methodOn(PromotionController.class)
                .revokePromotion(offerId, offeringId, promotionType))
                .withRel(REL_REVOKE_PROMOTION);
    }

}
