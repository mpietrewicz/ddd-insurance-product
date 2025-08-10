package pl.mpietrewicz.insurance.product.domainapi;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.List;

public interface PromotionApplicationService {

    List<PromotionType> getAvailablePromotions(OfferId offerId, ProductId productId);

    void addPromotion(PromotionType promotionType, OfferId offerId, ProductId productId);

    void removePromotion(PromotionType promotionType, OfferId offerId, ProductId productId);

}
