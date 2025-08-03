package pl.mpietrewicz.insurance.product.domain.service.promotion.policy.impl;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainPolicy;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.UsedPromotion;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionAvailabilityPolicy;

import java.time.LocalDate;
import java.util.List;

@DomainPolicy
public class PromotionAfterTwoYearsPolicy implements PromotionAvailabilityPolicy {

    private static final long TWO_YEARS = 2;

    @Override
    public boolean isSupportedBy(Product product) {
        return product.getPromotionType() == PromotionType.PROMOTION_AFTER_TWO_YEARS;
    }

    @Override
    public boolean isAvailable(LocalDate offerStartDate, List<UsedPromotion> usedPromotions) {
        return usedPromotions.isEmpty() || usedPromotions.stream()
                .map(UsedPromotion::getEndDate)
                .allMatch(promotionEnd -> promotionEnd.isBefore(offerStartDate.minusYears(TWO_YEARS)));
    }

}
