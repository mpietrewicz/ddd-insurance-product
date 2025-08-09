package pl.mpietrewicz.insurance.product.domain.service.promotion;

import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionPolicy;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.Optional;

public interface PromotionPolicyProvider {

    Optional<PromotionPolicy> getPolicy(PromotionType promotionType);

}
