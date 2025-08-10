package pl.mpietrewicz.insurance.product.domainapi.dto.offering;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;

@Getter
@RequiredArgsConstructor
public class AvailableOffering {

    private final ProductId productId;

    private final Premium premium;

}
