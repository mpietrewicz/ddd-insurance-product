package pl.mpietrewicz.insurance.product.domain.service.offer.factory.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.service.offer.factory.AvailableOfferingFactory;
import pl.mpietrewicz.insurance.product.domain.service.promotion.PromotionService;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class AvailableOfferingFactoryImpl implements AvailableOfferingFactory {

    private final PromotionService promotionService;

    @Override
    public AvailableOffering create(Product product, List<Contract> contracts, LocalDate offerStartDate) {
        ProductId productId = product.getProductId();
        List<PromotionType> availablePromotions = promotionService.getAvailablePromotions(product, contracts, offerStartDate);

        return new AvailableOffering(productId, availablePromotions);
    }

}
