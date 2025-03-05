package pl.mpietrewicz.insurance.product.domain.service.product.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.service.product.ProductAvailabilityService;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

import java.time.LocalDate;
import java.util.List;

import static java.util.function.Predicate.not;

@DomainService
@RequiredArgsConstructor
public class ProductAvailabilityServiceImpl implements ProductAvailabilityService {

    @Override
    public List<Product> getAvailableProductsFor(LocalDate startDate, ApplicantData applicantData, List<Product> products) {
        return products.stream()
                .filter(product -> product.isEligible(applicantData))
                .filter(product -> product.isAvailableAt(startDate))
                .toList();
    }

    @Override
    public List<Product> getAvailableProductsFor(Offer offer, ApplicantData applicantData, List<Product> products) {
        LocalDate offerStartDate = offer.getStartDate();
        List<Product> notOfferedProducts = getNotOfferedProducts(offer, products);

        return getAvailableProductsFor(offerStartDate, applicantData, notOfferedProducts);
    }

    private List<Product> getNotOfferedProducts(Offer offer, List<Product> allProducts) {
        return allProducts.stream()
                .filter(not(product -> offer.contains(product.getAggregateId())))
                .toList();
    }

}
