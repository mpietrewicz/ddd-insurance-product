package pl.mpietrewicz.insurance.product.webapi.service.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domainapi.OfferService;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;
import pl.mpietrewicz.insurance.product.webapi.service.adapter.OfferServiceAdapter;
import pl.mpietrewicz.insurance.product.webapi.dto.converter.ApplicantDataConverter;
import pl.mpietrewicz.insurance.product.webapi.dto.request.ApplicantDataRequest;
import pl.mpietrewicz.insurance.product.webapi.dto.request.CreateOfferRequest;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OfferServiceAdapterImpl implements OfferServiceAdapter {

    private final ApplicantDataConverter applicantDataConverter;

    private final OfferService offerService;

    @Override
    public boolean canCreateOffer(ApplicantDataRequest applicantDataRequest) {
        ApplicantData applicantData = applicantDataConverter.convert(applicantDataRequest);
        return offerService.canCreateOffer(applicantData);
    }

    @Override
    public OfferId createOffer(CreateOfferRequest createOfferRequest) {
        ApplicantDataRequest applicantDataRequest = createOfferRequest.getApplicantData();
        LocalDate startDate = createOfferRequest.getStartDate();

        ApplicantData applicantData = applicantDataConverter.convert(applicantDataRequest);
        return offerService.createOffer(applicantData, startDate);
    }

}
