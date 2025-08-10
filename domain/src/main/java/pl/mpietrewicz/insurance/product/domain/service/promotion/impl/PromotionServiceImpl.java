package pl.mpietrewicz.insurance.product.domain.service.promotion.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.UsedPromotion;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.service.promotion.PromotionService;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionPolicy;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@DomainService
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final Map<PromotionType, PromotionPolicy> promotionPolicyMap;

    @Override
    public List<PromotionType> getAvailablePromotions(Offer offer, Product product, List<Contract> contracts) {
        List<PromotionType> supportedPromotions = product.getSupportedPromotions();
        List<UsedPromotion> usedPromotions = getUsedPromotions(product, contracts);
        LocalDate offerStart = offer.getStartDate();

        return supportedPromotions.stream()
                .filter(allowedByPolicy(offerStart, usedPromotions))
                .filter(getAllowedByOffer(offer, product))
                .toList();
    }

    @Override
    public void addPromotion(PromotionType promotionType, Offer offer, Product product, List<Contract> contracts) {
        List<PromotionType> availablePromotions = getAvailablePromotions(offer, product, contracts);
        if (availablePromotions.contains(promotionType)) {
            PromotionPolicy promotionPolicy = promotionPolicyMap.get(promotionType);
            offer.applyPromotion(product.getProductId(), promotionPolicy, promotionType);
        }
    }

    private List<UsedPromotion> getUsedPromotions(Product product, List<Contract> contracts) {
        return contracts.stream()
                .flatMap(contract -> contract.getUsedPromotions(product.getProductId()).stream())
                .toList();
    }

    private Predicate<PromotionType> allowedByPolicy(LocalDate offerStart, List<UsedPromotion> usedPromotions) {
        return type -> {
            PromotionPolicy policy = promotionPolicyMap.get(type);
            return policy.canOffer(offerStart, usedPromotions);
        };
    }

    private Predicate<PromotionType> getAllowedByOffer(Offer offer, Product product) {
        return type -> offer.canApplyPromotion(type, product.getProductId()); // todo: tutaj powinienem przekazać
        // offeringId
    }

}
