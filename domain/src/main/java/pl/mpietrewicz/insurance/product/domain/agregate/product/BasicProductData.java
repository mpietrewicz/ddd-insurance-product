package pl.mpietrewicz.insurance.product.domain.agregate.product;

import lombok.Builder;
import lombok.Getter;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;

@Builder
@Getter
public class BasicProductData {

    private String name;

    private Premium premium;

    private LocalDate validFrom;

    private LocalDate validTo;

    private PromotionType promotionType;

}