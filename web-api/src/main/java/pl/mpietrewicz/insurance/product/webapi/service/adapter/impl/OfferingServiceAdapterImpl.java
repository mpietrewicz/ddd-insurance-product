package pl.mpietrewicz.insurance.product.webapi.service.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domainapi.OfferingService;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;
import pl.mpietrewicz.insurance.product.webapi.service.adapter.OfferingServiceAdapter;
import pl.mpietrewicz.insurance.product.webapi.dto.converter.ApplicantDataConverter;
import pl.mpietrewicz.insurance.product.webapi.dto.converter.AvailableOfferingConverter;
import pl.mpietrewicz.insurance.product.webapi.dto.request.AddOfferingRequest;
import pl.mpietrewicz.insurance.product.webapi.dto.response.AvailableOfferingModel;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferingServiceAdapterImpl implements OfferingServiceAdapter {

    private final ApplicantDataConverter applicantDataConverter;

    private final OfferingService offeringService;

    private final AvailableOfferingConverter availableOfferingConverter;

    @Override
    public CollectionModel<AvailableOfferingModel> getAvailableOfferings(OfferId offerId) {
        List<AvailableOffering> availableOfferings = offeringService.getAvailableOfferings(offerId);
        return availableOfferingConverter.convert(availableOfferings);
    }

    @Override
    public Long addOffering(OfferId offerId, AddOfferingRequest addOfferingRequest) {
        ProductId productId = addOfferingRequest.getProductId();
        Boolean promotion = addOfferingRequest.getPromotion();

        return offeringService.addOffering(offerId, productId, promotion);
    }

}
