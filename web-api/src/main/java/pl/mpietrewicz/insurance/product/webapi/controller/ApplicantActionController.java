package pl.mpietrewicz.insurance.product.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.product.readmodel.repository.OfferReadRepository;
import pl.mpietrewicz.insurance.product.webapi.service.adapter.OfferServiceAdapter;
import pl.mpietrewicz.insurance.product.webapi.dto.request.ApplicantDataRequest;

import static pl.mpietrewicz.insurance.product.webapi.controller.ApplicantOfferController.getLinkToCreateOffer;
import static pl.mpietrewicz.insurance.product.webapi.controller.ApplicantOfferController.getLinkToGetOffers;

@RestController
@RequestMapping("/applicant")
@RequiredArgsConstructor
@Tag(name = "Applicant Actions", description = "Actions available to the insurance applicant")
public class ApplicantActionController {

    private final OfferServiceAdapter offerServiceAdapter;

    private final OfferReadRepository offerReadRepository;

    @Operation(summary = "Get available actions for an insurance applicant",
            description = "Returns HATEOAS links for available actions related to an insurance applicant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available links for the insurance applicant",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RepresentationModel.class)))
    })
    @PostMapping
    public RepresentationModel<?> getApplicantLinks(@Valid @RequestBody ApplicantDataRequest applicantDataRequest) {
        RepresentationModel<?> model = new RepresentationModel<>();
        ApplicantId applicantId = applicantDataRequest.getApplicantId();

        model.add(getLinkToGetOffers(applicantId).withRel("offers"));

        if (offerServiceAdapter.canCreateOffer(applicantDataRequest)) {
            model.add(getLinkToCreateOffer());
        }
        return model;
    }

}
