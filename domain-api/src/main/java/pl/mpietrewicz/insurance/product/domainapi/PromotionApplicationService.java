package pl.mpietrewicz.insurance.product.domainapi;

import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingKey;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.List;

public interface PromotionApplicationService {

    List<PromotionType> getAvailablePromotions(OfferingKey offeringKey); // todo: wszędzie List<PromotionType>
    // zamienić na Set<PromotionType>

    void applyPromotion(PromotionType promotionType, OfferingKey offeringKey);

    List<PromotionType> listRevocablePromotions(OfferingKey offeringKey);

    void revokePromotion(PromotionType promotionType, OfferingKey offeringKey);

}
