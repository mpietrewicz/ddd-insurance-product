package pl.mpietrewicz.insurance.product.domainapi.dto.product;

import lombok.Builder;
import lombok.Getter;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class ProductData {

    private String name;

    private Premium premium;

    private Boolean canBePromotional;

    private LocalDate validFrom;

    private LocalDate validTo;

    private InsuredRequirements insuredRequirements;

    private List<PromotionType> promotionTypes;

}
