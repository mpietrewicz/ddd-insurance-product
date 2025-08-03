package pl.mpietrewicz.insurance.product.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.product.domain.repository.AccountingRepository;
import pl.mpietrewicz.insurance.product.domain.snapshot.Accounting;
import pl.mpietrewicz.insurance.product.repository.AccountingJpaRepository;
import pl.mpietrewicz.insurance.product.repository.AccountingWriteRepository;

@Service
@RequiredArgsConstructor
public class AccountingRepositoryImpl implements AccountingRepository, AccountingWriteRepository {

    private final AccountingJpaRepository jpaRepository;

    @Override
    public AccountingDate getAccountingDate() {
        return jpaRepository.findById(1L)
                .map(Accounting::getAccountingDate)
                .orElseThrow(() -> new IllegalStateException("Accounting record not found"));
    }

    @Override
    public void upsertAccountingDate(AccountingDate accountingDate) {
        Accounting accounting = jpaRepository.findById(1L)
                .orElseGet(Accounting::new);
        accounting.setAccountingDate(accountingDate);
        jpaRepository.save(accounting);
    }

}
