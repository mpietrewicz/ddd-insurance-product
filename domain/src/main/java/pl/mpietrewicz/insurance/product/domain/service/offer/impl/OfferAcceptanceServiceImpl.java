package pl.mpietrewicz.insurance.product.domain.service.offer.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainService;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.dto.AcceptedOffer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.ContractFactory;
import pl.mpietrewicz.insurance.product.domain.service.offer.OfferAcceptanceService;

@DomainService
@RequiredArgsConstructor
public class OfferAcceptanceServiceImpl implements OfferAcceptanceService {

    private final ContractFactory contractFactory;

    @Override
    public boolean canAcceptOffer(Offer offer, AccountingDate accountingDate) {
        return offer.canAcceptOffer(accountingDate);
    }

    @Override
    public Contract acceptOffer(Offer offer, AccountingDate accountingDate) {
        AcceptedOffer acceptedOffer = offer.accept(accountingDate);
        return contractFactory.create(acceptedOffer);
    }

}
