package pl.mpietrewicz.insurance.product.domain.agregate.contract;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class UsedPromotion {

    private final PromotionType promotionType;

    private final LocalDate startDate;

    private final LocalDate endDate;

}
