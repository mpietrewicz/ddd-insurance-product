package pl.mpietrewicz.insurance.product.domain.service.promotion;

import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;
import java.util.List;

public interface PromotionService {

    List<PromotionType> getAvailablePromotions(Product product, List<Contract> contracts, LocalDate offerStartDate);

    boolean calculateDiscount(PromotionType promotionType, Product product);

}
