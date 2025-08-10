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
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingKey;
import pl.mpietrewicz.insurance.product.domainapi.exception.ProductNotAvailableException;
import pl.mpietrewicz.insurance.product.webapi.dto.request.AddOfferingRequest;
import pl.mpietrewicz.insurance.product.webapi.dto.response.AvailableOfferingModel;
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferingModel;
import pl.mpietrewicz.insurance.product.webapi.service.adapter.OfferingServiceAdapter;
import pl.mpietrewicz.insurance.product.webapi.service.model.OfferingModelService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/offers/{offerId}/offerings")
@RequiredArgsConstructor
@Tag(name = "Offerings", description = "Managing offerings for a specific offer")
public class OfferingController {

    private final OfferingApplicationService offeringApplicationService;

    private final OfferingModelService offeringModelService;

    private final OfferingServiceAdapter offeringServiceAdapter;

    @Operation(summary = "Get offerings for a specific offer",
            description = "Retrieves all offerings associated with a specific offer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offerings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Offer not found")
    })
    @GetMapping
    public CollectionModel<OfferingModel> getOfferings(@PathVariable OfferId offerId) {
        CollectionModel<OfferingModel> offeringsModel = offeringModelService.getBy(offerId);

        offeringsModel.forEach(offering ->
                offering.add(getLinkToGetOffering(offerId, offering.getId())));
        offeringsModel.add(getLinkToGetAvailableOfferings(offerId));
        return offeringsModel;
    }

    @Operation(summary = "Get a specific offering",
            description = "Retrieves the details of a specific offering within a particular offer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offering retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Offering not found")
    })
    @GetMapping("/{offeringId}")
    public OfferingModel getOffering(@PathVariable OfferId offerId, @PathVariable Long offeringId) {
        OfferingModel offeringModel = offeringModelService.getBy(offerId, offeringId);

        offeringModel.add(getLinkToDeleteOffering(offerId, offeringId));
        return offeringModel;
    }

    @Operation(summary = "Get available offerings for a specific insured",
            description = "Retrieves a list of available offerings for a specific insured based on eligibility.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available offerings retrieved successfully")
    })
    @GetMapping("/available")
    public CollectionModel<AvailableOfferingModel> getAvailableOfferings(@PathVariable OfferId offerId) {
        CollectionModel<AvailableOfferingModel> availableOfferings = offeringServiceAdapter.getAvailableOfferings(offerId);

        if (!availableOfferings.getContent().isEmpty()) {
            availableOfferings.add(getLinkToAddOffering(offerId));
        }
        return availableOfferings;
    }

    @Operation(summary = "Add a new offering",
            description = "Adds a new offering to the specified offer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Offering added successfully"),
            @ApiResponse(responseCode = "404", description = "Offer or product not found"),
            @ApiResponse(responseCode = "409", description = "Offering with requested product id cannot be added")
    })
    @PostMapping
    public ResponseEntity<RepresentationModel<?>> addOffering(@PathVariable OfferId offerId,
              @Valid @RequestBody AddOfferingRequest addOfferingRequest) {
        Long offeringId = offeringServiceAdapter.addOffering(offerId, addOfferingRequest);
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
    public ResponseEntity<RepresentationModel<?>> deleteOffering(@PathVariable OfferId offerId,
                                                                 @PathVariable Long offeringId) {
        OfferingKey offeringKey = OfferingKey.of(offerId, offeringId);
        offeringApplicationService.removeOffering(offeringKey);

        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(getLinkToGetOfferings(offerId).withRel("other-offerings"));
        return ResponseEntity.ok()
                .body(model);
    }

    @ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<Void> handleNotAvailable(ProductNotAvailableException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    public static Link getLinkToGetOfferings(OfferId offerId) {
        return linkTo(methodOn(OfferingController.class)
                .getOfferings(offerId)).withSelfRel();
    }

    @SneakyThrows
    public static Link getLinkToAddOffering(OfferId offerId) {
        return linkTo(methodOn(OfferingController.class)
                .addOffering(offerId, null)).withRel("add-offering");
    }

    public static Link getLinkToGetOffering(OfferId offerId, Long offeringId) {
        return linkTo(methodOn(OfferingController.class)
                .getOffering(offerId, offeringId)).withSelfRel();
    }

    public static Link getLinkToGetAvailableOfferings(OfferId offerId) {
        return linkTo(methodOn(OfferingController.class)
                .getAvailableOfferings(offerId)).withRel("available-offerings");
    }

    public static Link getLinkToDeleteOffering(OfferId offerId, Long offeringId) {
        return linkTo(methodOn(OfferingController.class)
                .deleteOffering(offerId, offeringId)).withRel("delete-offering");
    }

}
