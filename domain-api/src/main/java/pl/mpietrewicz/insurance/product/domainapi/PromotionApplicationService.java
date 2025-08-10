package pl.mpietrewicz.insurance.product.domainapi;

import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingKey;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.List;

public interface PromotionApplicationService {

    List<PromotionType> getAvailablePromotions(OfferingKey offeringKey);

    void addPromotion(PromotionType promotionType, OfferingKey offeringKey);

    void removePromotion(PromotionType promotionType, OfferingKey offeringKey);

}
