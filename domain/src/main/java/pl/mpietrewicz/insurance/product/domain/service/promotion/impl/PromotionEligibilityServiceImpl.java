package pl.mpietrewicz.insurance.product.domain.service.promotion.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.UsedPromotion;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.service.promotion.PromotionEligibilityService;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionAvailabilityPolicy;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.impl.NoPromotionPolicy;

import java.time.LocalDate;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class PromotionEligibilityServiceImpl implements PromotionEligibilityService {

    private final List<PromotionAvailabilityPolicy> promotionPolicies;

    private final NoPromotionPolicy noPromotionPolicy;

    @Override
    public boolean canOfferPromotion(Product product, List<Contract> contracts, LocalDate offerStartDate) {
        PromotionAvailabilityPolicy promotionAvailabilityPolicy = getPromotionPolicy(product);
        List<UsedPromotion> usedPromotions = getUsedPromotions(product, contracts);

        return promotionAvailabilityPolicy.isAvailable(offerStartDate, usedPromotions);
    }

    private static List<UsedPromotion> getUsedPromotions(Product product, List<Contract> contracts) {
        return contracts.stream()
                .flatMap(contract -> contract.getUsedPromotions(product.getProductId()).stream())
                .toList();
    }

    private PromotionAvailabilityPolicy getPromotionPolicy(Product product) {
        return promotionPolicies.stream()
                .filter(policy -> policy.isSupportedBy(product))
                .findAny()
                .orElseGet(() -> noPromotionPolicy);
    }

}
