package pl.mpietrewicz.insurance.product.domainapi;

import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingKey;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.List;

public interface PromotionApplicationService {

    List<PromotionType> getAvailablePromotions(OfferingKey offeringKey);

    void applyPromotion(PromotionType promotionType, OfferingKey offeringKey);

    void revokePromotion(PromotionType promotionType, OfferingKey offeringKey);

}
