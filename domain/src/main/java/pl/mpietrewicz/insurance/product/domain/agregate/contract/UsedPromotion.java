package pl.mpietrewicz.insurance.product.domain.agregate.contract;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class UsedPromotion {

    private final LocalDate startDate;

    private final LocalDate endDate;

}
