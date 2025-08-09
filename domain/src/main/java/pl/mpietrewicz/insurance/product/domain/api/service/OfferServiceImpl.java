package pl.mpietrewicz.insurance.product.domain.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.mpietrewicz.insurance.ddd.annotations.application.ApplicationService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.events.ApplicantInsuredEvent;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.OfferNotFoundException;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.repository.AccountingRepository;
import pl.mpietrewicz.insurance.product.domain.repository.ApplicantRepository;
import pl.mpietrewicz.insurance.product.domain.repository.ContractRepository;
import pl.mpietrewicz.insurance.product.domain.repository.OfferRepository;
import pl.mpietrewicz.insurance.product.domain.repository.ProductRepository;
import pl.mpietrewicz.insurance.product.domain.service.aplicant.ApplicantDataProvider;
import pl.mpietrewicz.insurance.product.domain.service.aplicant.ApplicantFactory;
import pl.mpietrewicz.insurance.product.domain.service.offer.OfferAcceptanceService;
import pl.mpietrewicz.insurance.product.domain.service.offer.OfferCreationService;
import pl.mpietrewicz.insurance.product.domain.service.offer.OfferStartDateService;
import pl.mpietrewicz.insurance.product.domain.snapshot.Applicant;
import pl.mpietrewicz.insurance.product.domainapi.OfferService;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

import java.time.LocalDate;
import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferAcceptanceService offerAcceptanceService;

    private final OfferCreationService offerCreationService;

    private final ProductRepository productRepository;

    private final OfferRepository offerRepository;

    private final ContractRepository contractRepository;

    private final AccountingRepository accountingRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final ApplicantFactory applicantFactory;

    private final ApplicantRepository applicantRepository;

    private final ApplicantDataProvider applicantDataProvider;

    private final OfferStartDateService offerStartDateService;

    @Override
    public boolean canCreateOffer(ApplicantData applicantData) {
        AccountingDate accountingDate = accountingRepository.getAccountingDate();
        List<Product> allProducts = productRepository.loadAll();
        InsuredId insuredId = new InsuredId(applicantData.getApplicantId());
        List<Contract> insuredContracts = contractRepository.findBy(insuredId);

        return offerCreationService.canCreateOffer(applicantData, allProducts, insuredContracts, accountingDate);
    }

    @Override
    public OfferId createOffer(ApplicantData applicantData, LocalDate startDate) {
        AccountingDate accountingDate = accountingRepository.getAccountingDate();
        List<Product> allProducts = productRepository.loadAll();
        InsuredId insuredId = new InsuredId(applicantData.getApplicantId());
        List<Contract> insuredContracts = contractRepository.findBy(insuredId);

        Applicant applicant = applicantFactory.create(applicantData);
        applicantRepository.save(applicant);

        Offer offer = offerCreationService.createOffer(applicantData, startDate, allProducts, insuredContracts, accountingDate);
        offerRepository.save(offer);
        return offer.getAggregateId();
    }

    @Override
    public void deleteOffer(OfferId offerId) {
        offerRepository.delete(offerId);
    }

    @Override
    public boolean canAcceptOffer(OfferId offerId) {
        Offer offer = getOffer(offerId);
        AccountingDate accountingDate = accountingRepository.getAccountingDate();

        return offerAcceptanceService.canAcceptOffer(offer, accountingDate);
    }

    @Override
    public ContractId acceptOffer(OfferId offerId) {
        Offer offer = getOffer(offerId);
        AccountingDate accountingDate = accountingRepository.getAccountingDate();

        Contract contract = offerAcceptanceService.acceptOffer(offer, accountingDate);

        contractRepository.save(contract);
        eventPublisher.publishEvent(new ApplicantInsuredEvent(offer.getApplicantId(), contract.getInsuredId()));

        return contract.getAggregateId();
    }

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
