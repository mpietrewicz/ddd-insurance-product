package pl.mpietrewicz.insurance.product.domain.agregate.offer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AcceptedOffer {

    private final LocalDate startDate;

    private final List<AcceptedProduct> acceptedProducts;

}
