package pl.mpietrewicz.insurance.product.domain.service.offer.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domainapi.exception.ProductNotAvailableException;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.dto.OfferingContext;
import pl.mpietrewicz.insurance.product.domain.service.offer.OfferingAvailabilityService;
import pl.mpietrewicz.insurance.product.domain.service.offer.factory.AvailableOfferingFactory;
import pl.mpietrewicz.insurance.product.domain.service.product.ProductAvailabilityService;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;

import java.time.LocalDate;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class OfferingAvailabilityServiceImpl implements OfferingAvailabilityService {

    private final ProductAvailabilityService productAvailabilityService;

    private final AvailableOfferingFactory availableOfferingFactory;

    @Override
    public List<AvailableOffering> getAvailableOfferings(ApplicantData applicantData, List<Product> allProducts,
                                                         List<Contract> allContracts, Offer offer) {
        List<Product> availableProducts = productAvailabilityService.getAvailableProductsFor(offer, applicantData,
                allProducts);
        LocalDate offerStartDate = offer.getStartDate();

        return availableProducts.stream()
                .map(product -> availableOfferingFactory.create(product, allContracts, offerStartDate))
                .toList();
    }

    @Override
    public Long addOffering(OfferingContext offeringContext, ApplicantData applicantData, List<Contract> allContracts,
                            List<Product> allProducts) {
        Offer offer = offeringContext.getOffer();

        if (isProductAvailable(offeringContext, applicantData, allContracts, allProducts)) {
            ProductId productId = offeringContext.getProductId();
            Premium premium = offeringContext.getPremium();
            boolean promotion = offeringContext.isPromotion();
            return offer.addOffering(productId, premium, promotion);
        } else {
            throw new ProductNotAvailableException(offer.getOfferId());
        }
    }

    private boolean isProductAvailable(OfferingContext offeringContext, ApplicantData applicantData,
                                       List<Contract> allContracts, List<Product> allProducts) {
        Offer offer = offeringContext.getOffer();
        ProductId productId = offeringContext.getProductId();
        boolean promotion = offeringContext.isPromotion();

        List<AvailableOffering> availableOfferings = getAvailableOfferings(applicantData, allProducts, allContracts, offer);
        return availableOfferings.stream()
                .filter(availableOffering -> availableOffering.getProductId().equals(productId))
                .anyMatch(availableOffering -> availableOffering.getPromotionOptions().contains(promotion));
    }

}
