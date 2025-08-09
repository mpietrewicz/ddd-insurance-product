package pl.mpietrewicz.insurance.product.domain.service.promotion.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
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

@DomainService
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final List<PromotionPolicy> promotionPolicies;

    private final NoPromotionPolicy noPromotionPolicy;

    private final PromotionPolicyProvider promotionPolicyProvider;

    @Override
    public List<PromotionType> getAvailablePromotions(Offer offer, Product product, List<Contract> contracts,
                                                      LocalDate offerStartDate) {
        List<UsedPromotion> usedPromotionsInContracts = getUsedPromotions(product, contracts);
        List<UsedPromotion> usedPromotionsInOffer = getUsedPromotions(product, offer);

        product.getAvailablePromotionTypes().stream()
                .filter(promotionType -> canOffer())

        List<PromotionPolicy> promotionPolicies = product.getAvailablePromotionTypes().stream()
                .map(promotionPolicyProvider::getPolicy)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(policy -> policy.canOffer(offerStartDate, usedPromotions))
                .map(get)
                .toList();


        return promotionPolicies.stream()
                .filter(policy -> policy.canOffer(offerStartDate, getUsedPromotions(product, contracts)))
                .toList();
        // todo: to tutaj powinienem zwracać od razu harmonogram składek po promocji
    }

    @Override
    public void addPromotion(PromotionType promotionType, Product product, List<Contract> contracts, LocalDate offerStartDate) {

    }

    @Override
    public boolean calculateDiscount(PromotionType promotionType, Product product) {
        return false;
    }

    private List<UsedPromotion> getUsedPromotions(Product product, List<Contract> contracts) {
        return contracts.stream()
                .flatMap(contract -> contract.getUsedPromotions(product.getProductId()).stream())
                .toList();
    }

    private List<UsedPromotion> getUsedPromotions(Product product, Offer offer) {
        return offer.getUserPromotions(product.getProductId());
    }

}
