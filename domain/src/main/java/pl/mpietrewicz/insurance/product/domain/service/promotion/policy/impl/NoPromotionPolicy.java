package pl.mpietrewicz.insurance.product.domain.service.promotion.policy.impl;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainPolicy;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.UsedPromotion;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionPolicy;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;
import java.util.List;

import static pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType.NO_PROMOTION;

@DomainPolicy
public class NoPromotionPolicy implements PromotionPolicy {

    @Override
    public boolean support(PromotionType promotionType) {
        return promotionType == NO_PROMOTION;
    }

    @Override
    public boolean canOffer(LocalDate offerStartDate, List<UsedPromotion> usedPromotions) {
        return true;
    }

    @Override
    public Premium calculateDiscount(Premium basePremium) {
        return Premium.ZERO;
    }

}
