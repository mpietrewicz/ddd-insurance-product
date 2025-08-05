package pl.mpietrewicz.insurance.product.domain.service.promotion.policy.impl;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainPolicy;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.UsedPromotion;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionPolicy;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;
import java.util.List;

import static pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType.PROMOTION_AFTER_TWO_YEARS;

@DomainPolicy
public class PromotionAfterTwoYearsPolicy implements PromotionPolicy {

    private static final PromotionType PROMOTION_TYPE = PROMOTION_AFTER_TWO_YEARS;
    private static final long TWO_YEARS = 2;

    @Override
    public boolean isSupportedBy(Product product) {
        return product.getPromotionTypes().contains(PROMOTION_TYPE);
    }

    @Override
    public PromotionType getPromotionType() {
        return PROMOTION_TYPE;
    }

    @Override
    public boolean canOffer(LocalDate offerStartDate, List<UsedPromotion> usedPromotions) {
        return usedPromotions.isEmpty() || usedPromotions.stream()
                .map(UsedPromotion::getEndDate)
                .allMatch(promotionEnd -> promotionEnd.isBefore(offerStartDate.minusYears(TWO_YEARS)));
    }

    @Override
    public Premium calculateDiscount(Premium basePremium) {
        return null;
    }

}
