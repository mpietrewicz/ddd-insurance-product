package pl.mpietrewicz.insurance.product.webapi.service.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.product.domainapi.PromotionApplicationService;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingKey;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;
import pl.mpietrewicz.insurance.product.webapi.service.adapter.PromotionServiceAdapter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceAdapterImpl implements PromotionServiceAdapter {

    private final PromotionApplicationService promotionApplicationService;

    @Override
    public CollectionModel<PromotionType> getAvailablePromotions(OfferingKey offeringKey) {
        List<PromotionType> availablePromotions = promotionApplicationService.getAvailablePromotions(offeringKey);
        return CollectionModel.of(availablePromotions);
    }

}
