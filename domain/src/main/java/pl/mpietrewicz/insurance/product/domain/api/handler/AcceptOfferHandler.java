package pl.mpietrewicz.insurance.product.domain.api.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.mpietrewicz.insurance.ddd.annotations.application.ApplicationService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.events.ApplicantInsuredEvent;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.OfferNotFoundException;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.repository.AccountingRepository;
import pl.mpietrewicz.insurance.product.domain.repository.ContractRepository;
import pl.mpietrewicz.insurance.product.domain.repository.OfferRepository;
import pl.mpietrewicz.insurance.product.domain.service.offer.OfferAcceptanceService;
import pl.mpietrewicz.insurance.product.domainapi.offer.OfferAcceptanceUseCase;

@ApplicationService
@RequiredArgsConstructor
public class AcceptOfferHandler implements OfferAcceptanceUseCase {

    private final OfferRepository offerRepository;

    private final OfferAcceptanceService offerAcceptanceService;

    private final ContractRepository contractRepository;

    private final AccountingRepository accountingRepository;

    private final ApplicationEventPublisher eventPublisher;

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

    private Offer getOffer(OfferId offerId) {
        return offerRepository.load(offerId)
                .orElseThrow(() -> new OfferNotFoundException(offerId));
    }

}
