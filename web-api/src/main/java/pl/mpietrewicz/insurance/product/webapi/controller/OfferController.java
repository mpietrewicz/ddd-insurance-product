package pl.mpietrewicz.insurance.product.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domainapi.OfferApplicationService;
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferModel;
import pl.mpietrewicz.insurance.product.webapi.service.model.OfferModelService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static pl.mpietrewicz.insurance.product.webapi.controller.ApplicantOfferController.getLinkToGetOffers;
import static pl.mpietrewicz.insurance.product.webapi.controller.OfferingController.getLinkToGetOffering;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
@Tag(name = "Offers", description = "Managing offers")
public class OfferController {

    private final OfferApplicationService offerApplicationService;

    private final OfferModelService offerModelService;

    @Operation(summary = "Get offer by ID",
            description = "Retrieves details of a specific offer, including its associated offerings.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Offer not found")
    })
    @GetMapping("/{offerId}")
    public OfferModel getOffer(@PathVariable OfferId offerId) {
        OfferModel offerModel = offerModelService.getBy(offerId);
        offerModel.getOfferings().getContent().forEach(offering ->
                offering.add(getLinkToGetOffering(offerId, offering.getId())));

        offerModel.add(OfferingController.getLinkToGetAvailableOfferings(offerId));
        return offerModel;
    }

    @Operation(summary = "Delete offer",
            description = "Deletes a specific offer and returns a link to other offers associated with the same applicant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Offer not found")
    })
    @DeleteMapping("/{offerId}")
    public ResponseEntity<RepresentationModel<?>> deleteOffer(@PathVariable OfferId offerId) {
        OfferModel offerModel = offerModelService.getBy(offerId);
        ApplicantId applicantId = offerModel.getApplicantId();
        offerApplicationService.deleteOffer(offerId);

        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(getLinkToGetOffers(applicantId).withRel("other-offers"));
        return ResponseEntity.ok()
                .body(model);
    }

    public static Link getLinkToGetOffer(OfferId offerId) {
        return linkTo(methodOn(OfferController.class)
                .getOffer(offerId)).withSelfRel();
    }

}
