package pl.mpietrewicz.insurance.product.domain.service.promotion.policy.impl;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainPolicy;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.UsedPromotion;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionAvailabilityPolicy;

import java.time.LocalDate;
import java.util.List;

@DomainPolicy
public class NoPromotionPolicy implements PromotionAvailabilityPolicy {

    @Override
    public boolean isSupportedBy(Product product) {
        return product.getPromotionType() == PromotionType.NO_PROMOTION;
    }

    @Override
    public boolean isAvailable(LocalDate offerStartDate, List<UsedPromotion> usedPromotions) {
        return false;
    }

}
