package pl.mpietrewicz.insurance.product.domain.service.promotion.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.product.domain.service.promotion.PromotionPolicyProvider;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionPolicy;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionPolicyProviderImpl implements PromotionPolicyProvider {

    private final List<PromotionPolicy> promotionPolicies;

    @Override
    public Optional<PromotionPolicy> getPolicy(PromotionType promotionType) {
        return promotionPolicies.stream()
                .filter(policy -> policy.support(promotionType))
                .findAny();
    }

}
