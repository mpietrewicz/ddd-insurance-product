package pl.mpietrewicz.insurance.product.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domainapi.OfferApplicationService;
import pl.mpietrewicz.insurance.product.domainapi.exception.CannotChangeStartDateException;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/offers/{offerId}/start")
@RequiredArgsConstructor
@Tag(name = "Offer actions", description = "Operations for managing offer acceptance and start date modifications")
public class OfferStartController {

    private final OfferApplicationService offerApplicationService;

    @Operation(summary = "Get possibility of changing offer start date",
            description = "Returns available actions for changing the offer start date. " +
                    "Does not return the current start date, which is available in the offer resource.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available actions returned successfully"),
            @ApiResponse(responseCode = "404", description = "Offer not found")
    })
    @GetMapping
    public RepresentationModel<?> canChangeStartDate(@PathVariable OfferId offerId) {
        RepresentationModel<?> model = new RepresentationModel<>();
        List<LocalDate> availableStartDates = offerApplicationService.getAvailableStartDates(offerId);

        for (LocalDate startDate : availableStartDates) {
            model.add(getLinkToChangeStartDate(offerId, startDate));
        }

        return model;
    }

    @Operation(summary = "Change offer start date",
            description = "Changing an existing offer start date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Offer not found"),
            @ApiResponse(responseCode = "409", description = "Offer start date cannot be changed")
    })
    @PatchMapping
    public ResponseEntity<RepresentationModel<?>> changeStartDate(@PathVariable OfferId offerId,
                                                                  @RequestParam LocalDate startDate) {
        offerApplicationService.changeStartDate(offerId, startDate);

        return ResponseEntity.ok()
                .body(new RepresentationModel<>());
    }

    @ExceptionHandler(CannotChangeStartDateException.class)
    public ResponseEntity<Void> handleCannotChange(CannotChangeStartDateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    public static Link getLinkToChangeStartDate(OfferId offerId, LocalDate startDate) {
        return linkTo(methodOn(OfferStartController.class)
                .changeStartDate(offerId, startDate)).withRel(startDate.toString());
    }

}
