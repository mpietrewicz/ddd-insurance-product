package pl.mpietrewicz.insurance.product.domainapi.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class InsuredRequirements {

    private final int maxEntryAge;

    private final boolean forHealthyOnly;

}
