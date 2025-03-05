package pl.mpietrewicz.insurance.product.domain.service.promotion;

import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;

import java.time.LocalDate;
import java.util.List;

public interface PromotionEligibilityService {

    boolean canOfferPromotion(Product product, List<Contract> contracts, LocalDate offerStartDate);

}
