package pl.mpietrewicz.insurance.product.domain.api.handler;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.application.ApplicationService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.repository.AccountingRepository;
import pl.mpietrewicz.insurance.product.domain.repository.ApplicantRepository;
import pl.mpietrewicz.insurance.product.domain.repository.ContractRepository;
import pl.mpietrewicz.insurance.product.domain.repository.OfferRepository;
import pl.mpietrewicz.insurance.product.domain.repository.ProductRepository;
import pl.mpietrewicz.insurance.product.domain.service.aplicant.ApplicantFactory;
import pl.mpietrewicz.insurance.product.domain.service.offer.OfferCreationService;
import pl.mpietrewicz.insurance.product.domain.snapshot.Applicant;
import pl.mpietrewicz.insurance.product.domainapi.offer.OfferCreationUseCase;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;

import java.time.LocalDate;
import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class CreateOfferHandler implements OfferCreationUseCase {

    private final OfferCreationService offerCreationService;

    private final ProductRepository productRepository;

    private final OfferRepository offerRepository;

    private final ContractRepository contractRepository;

    private final AccountingRepository accountingRepository;

    private final ApplicantFactory applicantFactory;

    private final ApplicantRepository applicantRepository;

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

}
