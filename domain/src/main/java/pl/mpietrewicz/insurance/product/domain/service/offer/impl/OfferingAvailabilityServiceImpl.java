package pl.mpietrewicz.insurance.product.domain.service.offer.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.dto.OfferingContext;
import pl.mpietrewicz.insurance.product.domain.service.offering.OfferingAvailabilityService;
import pl.mpietrewicz.insurance.product.domain.service.offer.factory.AvailableOfferingFactory;
import pl.mpietrewicz.insurance.product.domain.service.product.ProductAvailabilityService;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;
import pl.mpietrewicz.insurance.product.domainapi.exception.ProductNotAvailableException;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class OfferingAvailabilityServiceImpl implements OfferingAvailabilityService {

    private final ProductAvailabilityService productAvailabilityService;

    private final AvailableOfferingFactory availableOfferingFactory;

    @Override
    public List<AvailableOffering> getAvailableOfferings(ApplicantData applicantData, List<Product> allProducts,
                                                         List<Contract> allContracts, Offer offer) {
        return productAvailabilityService.getAvailableProductsFor(offer, applicantData,allProducts).stream()
                .map(availableOfferingFactory::create)
                .toList();
    }

    @Override
    public Long addOffering(OfferingContext offeringContext, ApplicantData applicantData, List<Contract> allContracts,
                            List<Product> allProducts) {
        Offer offer = offeringContext.getOffer();

        if (isProductAvailable(offeringContext, applicantData, allContracts, allProducts)) {
            Product product = offeringContext.getProduct();
            ProductId productId = product.getProductId();
            Premium premium = product.getPremium();
            return offer.addOffering(productId, premium);
        } else {
            throw new ProductNotAvailableException(offer.getOfferId());
        }
    }

    private boolean isProductAvailable(OfferingContext offeringContext, ApplicantData applicantData,
                                       List<Contract> allContracts, List<Product> allProducts) {
        Offer offer = offeringContext.getOffer();
        Product product = offeringContext.getProduct();
        ProductId productId = product.getProductId();

        List<AvailableOffering> availableOfferings = getAvailableOfferings(applicantData, allProducts, allContracts, offer);
        return availableOfferings.stream()
                .anyMatch(offering -> offering.getProductId().equals(productId));
    }

}
