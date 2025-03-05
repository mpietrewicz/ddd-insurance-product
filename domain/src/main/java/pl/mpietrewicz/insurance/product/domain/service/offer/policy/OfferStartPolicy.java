package pl.mpietrewicz.insurance.product.domain.service.offer.policy;

import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;

import java.time.LocalDate;
import java.util.List;

public interface OfferStartPolicy {

    List<LocalDate> determine(AccountingDate accountingDate);

}
