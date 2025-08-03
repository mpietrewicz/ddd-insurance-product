package pl.mpietrewicz.insurance.product.domain.service.promotion.policy;

import pl.mpietrewicz.insurance.product.domain.agregate.contract.UsedPromotion;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;

import java.time.LocalDate;
import java.util.List;

public interface PromotionAvailabilityPolicy {

    boolean isSupportedBy(Product product);

    boolean isAvailable(LocalDate offerStartDate, List<UsedPromotion> usedPromotions);

}
