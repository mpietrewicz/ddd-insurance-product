package pl.mpietrewicz.insurance.product.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

@Getter
@RequiredArgsConstructor
public class OfferingContext {

    private final ProductId productId;
    private final Premium premium;
    private final PromotionType promotionType;
    private final Offer offer;

}
