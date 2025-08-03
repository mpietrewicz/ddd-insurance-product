package pl.mpietrewicz.insurance.product.domain.service.offer;

import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

import java.time.LocalDate;
import java.util.List;

public interface OfferStartDateService {

    List<LocalDate> getAvailableStartDates(Offer offer, ApplicantData applicantData,
                                           List<Product> allProducts, AccountingDate accountingDate);

}
