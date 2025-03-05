package pl.mpietrewicz.insurance.product.domain.service.offer.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.product.domainapi.exception.CannotCreateOfferException;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.OfferFactory;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.service.offer.OfferCreationService;
import pl.mpietrewicz.insurance.product.domain.service.offer.policy.OfferStartPolicy;
import pl.mpietrewicz.insurance.product.domain.service.product.ProductAvailabilityService;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

import java.time.LocalDate;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class OfferCreationServiceImpl implements OfferCreationService {

    private final OfferStartPolicy offerStartPolicy;

    private final ProductAvailabilityService productAvailabilityService;

    private final OfferFactory offerFactory;

    @Override
    public boolean canCreateOffer(ApplicantData applicantData, List<Product> allProducts,
                                  List<Contract> insuredContracts, AccountingDate accountingDate) {
        List<LocalDate> offerStartDates = offerStartPolicy.determine(accountingDate);
        return hasNoActiveContracts(insuredContracts) && offerStartDates.stream()
                .anyMatch(startDate -> hasAnyAvailableProduct(startDate, applicantData, allProducts));
    }

    @Override
    public Offer createOffer(ApplicantData applicantData, LocalDate startDate, List<Product> allProducts,
                             List<Contract> insuredContracts, AccountingDate accountingDate) {
        ApplicantId applicantId = applicantData.getApplicantId();

        if (canCreateOffer(applicantData, allProducts, insuredContracts, accountingDate)) {
            return offerFactory.create(applicantId, startDate);
        } else {
            throw new CannotCreateOfferException(applicantId);
        }
    }

    private boolean hasNoActiveContracts(List<Contract> contracts) {
        return contracts.stream()
                .noneMatch(Contract::isActive);
    }

    private boolean hasAnyAvailableProduct(LocalDate startDate, ApplicantData applicantData, List<Product> allProducts) {
        return !productAvailabilityService.getAvailableProductsFor(startDate, applicantData, allProducts)
                .isEmpty();
    }

}
