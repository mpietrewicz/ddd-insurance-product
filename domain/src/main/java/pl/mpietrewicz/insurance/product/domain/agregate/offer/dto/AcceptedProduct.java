package pl.mpietrewicz.insurance.product.domain.agregate.offer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;

@Getter
@RequiredArgsConstructor
public class AcceptedProduct {

    private final ProductId productId;

    private final boolean promotional;

    private final Premium premium;

}
