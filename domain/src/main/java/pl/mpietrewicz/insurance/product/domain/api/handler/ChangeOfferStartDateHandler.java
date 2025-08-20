package pl.mpietrewicz.insurance.product.domain.api.handler;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.application.ApplicationService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.OfferNotFoundException;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.repository.AccountingRepository;
import pl.mpietrewicz.insurance.product.domain.repository.OfferRepository;
import pl.mpietrewicz.insurance.product.domain.repository.ProductRepository;
import pl.mpietrewicz.insurance.product.domain.service.aplicant.ApplicantDataProvider;
import pl.mpietrewicz.insurance.product.domain.service.offer.OfferStartDateService;
import pl.mpietrewicz.insurance.product.domainapi.offer.OfferSchedulingUseCase;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

import java.time.LocalDate;
import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class ChangeOfferStartDateHandler implements OfferSchedulingUseCase {

    private final ProductRepository productRepository;

    private final OfferRepository offerRepository;

    private final AccountingRepository accountingRepository;

    private final ApplicantDataProvider applicantDataProvider;

    private final OfferStartDateService offerStartDateService;

    @Override
    public List<LocalDate> getAvailableStartDates(OfferId offerId) {
        AccountingDate accountingDate = accountingRepository.getAccountingDate();
        Offer offer = getOffer(offerId);
        List<Product> allProducts = productRepository.loadAll();
        ApplicantData applicantData = applicantDataProvider.get(offer.getApplicantId());

        return offerStartDateService.getAvailableStartDates(offer, applicantData, allProducts, accountingDate);
    }

    @Override
    public void changeStartDate(OfferId offerId, LocalDate startDate) {
        if (getAvailableStartDates(offerId).contains(startDate)) {
            Offer offer = getOffer(offerId);
            offer.changeStartDate(startDate);
        }
    }

    private Offer getOffer(OfferId offerId) {
        return offerRepository.load(offerId)
                .orElseThrow(() -> new OfferNotFoundException(offerId));
    }

}
