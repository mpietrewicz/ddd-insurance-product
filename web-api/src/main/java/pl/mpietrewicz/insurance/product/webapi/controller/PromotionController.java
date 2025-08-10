package pl.mpietrewicz.insurance.product.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domainapi.OfferingApplicationService;
import pl.mpietrewicz.insurance.product.domainapi.PromotionApplicationService;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingId;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;
import pl.mpietrewicz.insurance.product.domainapi.exception.ProductNotAvailableException;
import pl.mpietrewicz.insurance.product.webapi.dto.request.AddOfferingRequest;
import pl.mpietrewicz.insurance.product.webapi.dto.response.AvailableOfferingModel;
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferingModel;
import pl.mpietrewicz.insurance.product.webapi.service.adapter.OfferingServiceAdapter;
import pl.mpietrewicz.insurance.product.webapi.service.adapter.PromotionServiceAdapter;
import pl.mpietrewicz.insurance.product.webapi.service.model.OfferingModelService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/offers/{offerId}/offerings/{offeringId}/promotions")
@RequiredArgsConstructor
@Tag(name = "Offerings", description = "Managing offerings for a specific offer")
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
        OfferingId offeringId1 = OfferingId.of(offerId, offeringId);
        CollectionModel<PromotionType> availablePromotions = promotionServiceAdapter.getAvailablePromotions(offeringId1);

        if (!availablePromotions.getContent().isEmpty()) {
            availablePromotions.add(getLinkToAddPromotion(offerId));
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
                                                               @PathVariable PromotionType promotionType) {
        OfferingId offeringId1 = OfferingId.of(offerId, offeringId);
        promotionApplicationService.addPromotion(promotionType, offeringId1);

        return ResponseEntity.created(getLinkToGetOffering(offerId, offeringId).toUri())
                .body(new RepresentationModel<>());
    }

    @Operation(summary = "Delete an offering",
            description = "Deletes a specific offering from the offer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offering deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Offering not found")
    })
    @DeleteMapping("/{offeringId}")
    public ResponseEntity<RepresentationModel<?>> removePromotion(@PathVariable OfferId offerId,
                                                                  @PathVariable Long offeringId,
                                                                  @PathVariable PromotionType promotionType) {
        OfferingId offeringId1 = OfferingId.of(offerId, offeringId);
        promotionApplicationService.removePromotion(promotionType, offeringId1);

        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(getLinkToGetOfferings(offerId).withRel("other-offerings")); // todo: co tutaj zwrócić ?
        return ResponseEntity.ok()
                .body(model);
    }

    @ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<Void> handleNotAvailable(ProductNotAvailableException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    public static Link getLinkToGetOfferings(OfferId offerId) {
        return linkTo(methodOn(PromotionController.class)
                .getOfferings(offerId)).withSelfRel();
    }

    @SneakyThrows
    public static Link getLinkToAddPromotion(OfferId offerId) {
        return linkTo(methodOn(PromotionController.class)
                .addPromotion(offerId, null)).withRel("add-offering");
    }

    public static Link getLinkToGetOffering(OfferId offerId, Long offeringId) {
        return linkTo(methodOn(PromotionController.class)
                .getOffering(offerId, offeringId)).withSelfRel();
    }

    public static Link getLinkToGetAvailableOfferings(OfferId offerId) {
        return linkTo(methodOn(PromotionController.class)
                .getAvailableOfferings(offerId)).withRel("available-offerings");
    }

    public static Link getLinkToDeleteOffering(OfferId offerId, Long offeringId) {
        return linkTo(methodOn(PromotionController.class)
                .deleteOffering(offerId, offeringId)).withRel("delete-offering");
    }

}
