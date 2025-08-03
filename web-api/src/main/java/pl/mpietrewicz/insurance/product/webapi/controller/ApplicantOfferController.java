package pl.mpietrewicz.insurance.product.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domainapi.exception.CannotCreateOfferException;
import pl.mpietrewicz.insurance.product.webapi.service.adapter.OfferServiceAdapter;
import pl.mpietrewicz.insurance.product.webapi.dto.request.CreateOfferRequest;
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferModel;
import pl.mpietrewicz.insurance.product.webapi.service.model.OfferModelService;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static pl.mpietrewicz.insurance.product.webapi.controller.OfferController.getLinkToGetOffer;

@RestController
@RequestMapping("/applicant/{applicantId}/offers")
@RequiredArgsConstructor
@Tag(name = "Applicant Offers", description = "Get offers for the insurance applicant")
public class ApplicantOfferController {

    private final OfferServiceAdapter offerServiceAdapter;

    private final OfferModelService offerModelService;

    @Operation(summary = "Get all offers for an applicant",
            description = "Returns all insurance offers associated with a given applicant ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offers retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OfferModel.class))))
    })
    @GetMapping
    public CollectionModel<OfferModel> getOffers(@PathVariable ApplicantId applicantId) {
        CollectionModel<OfferModel> offersModel = offerModelService.getByApplicantId(applicantId);
        offersModel.getContent().forEach(offer ->
                offer.add(getLinkToGetOffer(offer.getId())));
        return offersModel;
    }

    @Operation(summary = "Create a new offer for an applicant",
            description = "Creates a new offer for the given applicant with the provided request data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Offer created successfully",
                    headers = @Header(name = HttpHeaders.LOCATION, description = "URI to the created offer")),
            @ApiResponse(responseCode = "409", description = "Offer cannot be created for given applicant")
    })
    @PostMapping
    public ResponseEntity<Void> createOffer(@Valid @RequestBody CreateOfferRequest createOfferRequest) {
        OfferId offerId = offerServiceAdapter.createOffer(createOfferRequest);

        URI offerLocation = getLinkToGetOffer(offerId).toUri();
        return ResponseEntity.created(offerLocation).build();
    }

    @ExceptionHandler(CannotCreateOfferException.class)
    public ResponseEntity<Void> handleConflict(CannotCreateOfferException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    public static Link getLinkToGetOffers(ApplicantId applicantId) {
        return linkTo(methodOn(ApplicantOfferController.class)
                .getOffers(applicantId)).withSelfRel();
    }

    @SneakyThrows
    public static Link getLinkToCreateOffer() {
        Link link = linkTo(methodOn(ApplicantOfferController.class)
                .createOffer(null)).withRel("create-offer");

        return Affordances.of(link)
                .afford(HttpMethod.POST)
                .toLink();
    }

}
