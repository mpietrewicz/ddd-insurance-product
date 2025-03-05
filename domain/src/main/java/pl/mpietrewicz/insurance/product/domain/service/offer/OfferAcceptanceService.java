package pl.mpietrewicz.insurance.product.domain.service.offer;

import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;

public interface OfferAcceptanceService {

    boolean canAcceptOffer(Offer offer, AccountingDate accountingDate);

    Contract acceptOffer(Offer offer, AccountingDate accountingDate);

}