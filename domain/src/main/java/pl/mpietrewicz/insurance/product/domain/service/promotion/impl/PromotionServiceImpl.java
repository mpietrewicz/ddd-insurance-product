package pl.mpietrewicz.insurance.product.domain.service.promotion.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.UsedPromotion;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.service.promotion.PromotionService;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionPolicy;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.impl.NoPromotionPolicy;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final List<PromotionPolicy> promotionPolicies;

    private final NoPromotionPolicy noPromotionPolicy;

    @Override
    public List<PromotionType> getAvailablePromotions(Product product, List<Contract> contracts,
                                                      LocalDate offerStartDate) {
        return promotionPolicies.stream()
                .filter(policy -> policy.isSupportedBy(product))
                .filter(policy -> policy.canOffer(offerStartDate, getUsedPromotions(product, contracts)))
                .map(PromotionPolicy::getPromotionType)
                .toList();
        // todo: to tutaj powinienem zwracać od razu harmonogram składek po promocji
    }

    @Override
    public boolean calculateDiscount(PromotionType promotionType, Product product) {
        return false;
    }

    private static List<UsedPromotion> getUsedPromotions(Product product, List<Contract> contracts) {
        return contracts.stream()
                .flatMap(contract -> contract.getUsedPromotions(product.getProductId()).stream())
                .toList();
    }

    private PromotionPolicy getPromotionPolicy(Product product) {
        return promotionPolicies.stream()
                .filter(policy -> policy.isSupportedBy(product))
                .findAny()
                .orElseGet(() -> noPromotionPolicy);
    }

}
