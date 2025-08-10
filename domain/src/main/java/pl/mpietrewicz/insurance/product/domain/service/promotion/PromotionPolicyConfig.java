package pl.mpietrewicz.insurance.product.domain.service.promotion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionPolicy;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
public class PromotionPolicyConfig {

    @Bean
    public Map<PromotionType, PromotionPolicy> promotionPolicyMap(List<PromotionPolicy> policies) {
        EnumMap<PromotionType, PromotionPolicy> byType = new EnumMap<>(PromotionType.class);

        for (PromotionType type : PromotionType.values()) {
            Optional<PromotionPolicy> matchedPolicy = policies.stream()
                    .filter(policy -> policy.getType() == type)
                    .reduce((a, b) -> {
                        throw new IllegalStateException("More than one policy supporting the type was found. " + type);
                    });

            if (matchedPolicy.isPresent()) {
                byType.put(type, matchedPolicy.get());
            } else {
                throw new IllegalStateException("Brak polityki dla typu: " + type);
            }
        }
        return Map.copyOf(byType);
    }

}
