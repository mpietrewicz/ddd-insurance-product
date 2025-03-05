package pl.mpietrewicz.insurance.product.domain.repository;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepository;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;

@DomainRepository
public interface AccountingRepository {

    /**
     * Returns the current accounting date used for insurance operations.
     *
     * @return the current accounting date.
     */
    AccountingDate getAccountingDate();

}
