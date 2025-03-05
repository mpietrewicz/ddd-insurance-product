package pl.mpietrewicz.insurance.product.repository;

import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;

public interface AccountingWriteRepository {

    /**
     * Sets the accounting date, which determines the date used for insurance operations.
     *
     * @param accountingDate the accounting date to set, encapsulated as an {@code AccountingDate} object
     */
    void upsertAccountingDate(AccountingDate accountingDate);

}
