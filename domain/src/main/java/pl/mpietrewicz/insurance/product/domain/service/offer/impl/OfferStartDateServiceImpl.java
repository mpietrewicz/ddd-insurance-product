package pl.mpietrewicz.insurance.product.domain.service.offer.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.service.offer.OfferStartDateService;
import pl.mpietrewicz.insurance.product.domain.service.offer.policy.OfferStartPolicy;
import pl.mpietrewicz.insurance.product.domain.service.product.ProductAvailabilityService;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

import java.time.LocalDate;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class OfferStartDateServiceImpl implements OfferStartDateService {

    private final ProductAvailabilityService productAvailabilityService;

    private final OfferStartPolicy offerStartPolicy;

    @Override
    public List<LocalDate> getAvailableStartDates(Offer offer, ApplicantData applicantData,
                                                  List<Product> allProducts, AccountingDate accountingDate) {
        List<LocalDate> availableStartDates = offer.getAvailableStartDates(offerStartPolicy, accountingDate);
        return availableStartDates.stream()
                .filter(startDate -> canStartAllOfferings(startDate, offer, applicantData, allProducts))
                .toList();
    }

    private boolean canStartAllOfferings(LocalDate startDate, Offer offer, ApplicantData applicantData,
                                         List<Product> allProducts) {
        List<ProductId> availableProductIds = productAvailabilityService
                .getAvailableProductsFor(startDate, applicantData, allProducts).stream()
                .map(Product::getProductId)
                .toList();
        return offer.contains(availableProductIds);
    }

}
