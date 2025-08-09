package pl.mpietrewicz.insurance.product.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;

@Getter
@RequiredArgsConstructor
public class OfferingContext {

    private final Offer offer;
    private final Product product;

}
