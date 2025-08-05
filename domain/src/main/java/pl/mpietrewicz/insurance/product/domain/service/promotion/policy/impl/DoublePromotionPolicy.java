package pl.mpietrewicz.insurance.product.domain.service.promotion.policy.impl;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainPolicy;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.UsedPromotion;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionPolicy;

import java.time.LocalDate;
import java.util.List;

import static pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType.DOUBLE_PROMOTION;

@DomainPolicy
public class DoublePromotionPolicy implements PromotionPolicy {

    private static final PromotionType PROMOTION_TYPE = DOUBLE_PROMOTION;

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
        return usedPromotions.size() < 2;
    }

    @Override
    public Premium calculateDiscount(Premium basePremium) {
        return null;
    }

}
