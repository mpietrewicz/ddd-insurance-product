package pl.mpietrewicz.insurance.product.domainapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AvailableOffering {

    private final ProductId productId;

    private final List<Boolean> promotionOptions;

}
