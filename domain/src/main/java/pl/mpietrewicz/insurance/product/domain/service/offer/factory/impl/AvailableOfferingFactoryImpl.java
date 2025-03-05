package pl.mpietrewicz.insurance.product.domain.service.offer.factory.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.service.offer.factory.AvailableOfferingFactory;
import pl.mpietrewicz.insurance.product.domain.service.promotion.PromotionEligibilityService;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;

import java.time.LocalDate;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class AvailableOfferingFactoryImpl implements AvailableOfferingFactory {

    private final PromotionEligibilityService promotionEligibilityService;

    @Override
    public AvailableOffering create(Product product, List<Contract> contracts, LocalDate offerStartDate) {
        ProductId productId = product.getProductId();
        boolean canOfferPromotion = promotionEligibilityService.canOfferPromotion(product, contracts, offerStartDate);
        List<Boolean> promotionOptions = createPromotionOptions(canOfferPromotion);

        return new AvailableOffering(productId, promotionOptions);
    }

    private List<Boolean> createPromotionOptions(boolean canBeOfferAsPromotional) {
        if (canBeOfferAsPromotional) {
            return List.of(Boolean.TRUE, Boolean.FALSE);
        } else {
            return List.of(Boolean.FALSE);
        }
    }

}
