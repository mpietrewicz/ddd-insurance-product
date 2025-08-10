package pl.mpietrewicz.insurance.product.webapi.service.adapter;

import org.springframework.hateoas.CollectionModel;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingId;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

public interface PromotionServiceAdapter {

    CollectionModel<PromotionType> getAvailablePromotions(OfferingId offeringId);

    void addPromotion(PromotionType promotionType, OfferId offerId, Long offeringId);

}
