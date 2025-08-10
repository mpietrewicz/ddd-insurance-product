package pl.mpietrewicz.insurance.product.domain.service.promotion;

import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.List;

public interface PromotionService {

    List<PromotionType> getAvailablePromotions(Offer offer, Product product, List<Contract> contracts);

    void addPromotion(PromotionType promotionType, Offer offer, Product product, List<Contract> contracts);

}
