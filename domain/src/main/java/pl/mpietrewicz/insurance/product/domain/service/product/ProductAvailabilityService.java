package pl.mpietrewicz.insurance.product.domain.service.product;

import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

import java.time.LocalDate;
import java.util.List;

public interface ProductAvailabilityService {

    List<Product> getAvailableProductsFor(LocalDate startDate, ApplicantData applicantData, List<Product> products);

    List<Product> getAvailableProductsFor(Offer offer, ApplicantData applicantData, List<Product> products);

}
