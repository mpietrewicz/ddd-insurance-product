package pl.mpietrewicz.insurance.product.webapi.service.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domainapi.OfferingApplicationService;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;
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

    private final OfferingApplicationService offeringApplicationService;

    private final AvailableOfferingConverter availableOfferingConverter;

    @Override
    public CollectionModel<AvailableOfferingModel> getAvailableOfferings(OfferId offerId) {
        List<AvailableOffering> availableOfferings = offeringApplicationService.getAvailableOfferings(offerId);
        return availableOfferingConverter.convert(availableOfferings);
    }

    @Override
    public Long addOffering(OfferId offerId, AddOfferingRequest addOfferingRequest) {
        ProductId productId = addOfferingRequest.getProductId();
        PromotionType promotionType = addOfferingRequest.getPromotionType();

        return offeringApplicationService.addOffering(offerId, productId, promotionType);
    }

}
