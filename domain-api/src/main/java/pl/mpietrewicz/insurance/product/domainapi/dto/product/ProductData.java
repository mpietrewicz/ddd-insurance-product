package pl.mpietrewicz.insurance.product.domainapi.dto.product;

import lombok.Builder;
import lombok.Getter;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;

import java.time.LocalDate;

@Builder
@Getter
public class ProductData {

    private String name;

    private Premium premium;

    private Boolean canBePromotional;

    private LocalDate validFrom;

    private LocalDate validTo;

    private InsuredRequirements insuredRequirements;

    private PromotionType promotionType;

}
