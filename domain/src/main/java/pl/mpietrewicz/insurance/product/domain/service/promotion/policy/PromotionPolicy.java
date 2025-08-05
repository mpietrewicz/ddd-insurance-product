package pl.mpietrewicz.insurance.product.domain.service.promotion.policy;

import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.UsedPromotion;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;
import java.util.List;

public interface PromotionPolicy {

    boolean isSupportedBy(Product product);

    PromotionType getPromotionType();

    boolean canOffer(LocalDate offerStartDate, List<UsedPromotion> usedPromotions);

    Premium calculateDiscount(Premium basePremium);

}
