package pl.mpietrewicz.insurance.product.webapi.service.adapter;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.webapi.dto.request.ApplicantDataRequest;
import pl.mpietrewicz.insurance.product.webapi.dto.request.CreateOfferRequest;

public interface OfferServiceAdapter {

    boolean canCreateOffer(ApplicantDataRequest applicantDataRequest);

    OfferId createOffer(CreateOfferRequest createOfferRequest);

}
