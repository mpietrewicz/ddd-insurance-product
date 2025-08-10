package pl.mpietrewicz.insurance.product.domainapi;

import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingId;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.List;

public interface PromotionApplicationService {

    List<PromotionType> getAvailablePromotions(OfferingId offeringId);

    void addPromotion(PromotionType promotionType, OfferingId offeringId);

    void removePromotion(PromotionType promotionType, OfferingId offeringId);

}
