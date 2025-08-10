package pl.mpietrewicz.insurance.product.domain.service.promotion.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.UsedPromotion;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.service.promotion.PromotionPolicyProvider;
import pl.mpietrewicz.insurance.product.domain.service.promotion.PromotionService;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionPolicy;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.impl.NoPromotionPolicy;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@DomainService
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final List<PromotionPolicy> promotionPolicies;

    private final NoPromotionPolicy noPromotionPolicy;

    private final PromotionPolicyProvider promotionPolicyProvider;

    @Override
    public List<PromotionType> getAvailablePromotions(Offer offer, Product product, List<Contract> contracts) {
        return product.getSupportedPromotions().stream()
                .filter(canOffer(product, offer, contracts))
                .filter(promotionType -> offer.canAddPromotion(product.getProductId(), promotionType))
                .toList();
    }

    @Override
    public void addPromotion(PromotionType promotionType, Offer offer, Product product, List<Contract> contracts) {
        List<PromotionType> availablePromotions = getAvailablePromotions(offer, product, contracts);
        if (availablePromotions.contains(promotionType)) {
            offer.addPromotion(product.getProductId(), promotionType);
        }
    }

    @Override
    public Premium calculateDiscount(PromotionType promotionType, Product product) {
        Optional<PromotionPolicy> promotionPolicy = promotionPolicies.stream()
                .filter(policy -> policy.support(promotionType))
                .findAny();
        if (promotionPolicy.isPresent()) {
            Premium premium = product.getPremium();
            return promotionPolicy.get().calculateDiscount(premium);
        } else {
            throw new IllegalArgumentException("Promotion policy not found for promotion type: " + promotionType);
        }
    }

    private Predicate<PromotionType> canOffer(Product product, Offer offer, List<Contract> contracts) {
        return promotionType -> {
            List<UsedPromotion> usedPromotions = getUsedPromotions(product, contracts);

            Optional<PromotionPolicy> promotionPolicy = promotionPolicies.stream()
                    .filter(policy -> policy.support(promotionType))
                    .findAny();
            LocalDate offerStartDate = offer.getStartDate();
            return promotionPolicy.map(policy -> policy.canOffer(offerStartDate, usedPromotions))
                    .orElse(false);
        };
    }

    private List<UsedPromotion> getUsedPromotions(Product product, List<Contract> contracts) {
        return contracts.stream()
                .flatMap(contract -> contract.getUsedPromotions(product.getProductId()).stream())
                .toList();
    }

}
