package pl.mpietrewicz.insurance.product.repository.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.product.repository.AccountingWriteRepository;
import pl.mpietrewicz.insurance.product.repository.init.service.ExternalAccountingClient;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountingDateInitializer implements ApplicationRunner {

    private final ExternalAccountingClient externalAccountingClient;

    private final AccountingWriteRepository AccountingWriteRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Initializing accounting date at application startup...");
        try {
            LocalDate fetchedDate = externalAccountingClient.fetchAccountingDate();
            AccountingDate accountingDate = AccountingDate.valueOf(fetchedDate);
            AccountingWriteRepository.upsertAccountingDate(accountingDate);

            log.info("Accounting date successfully set to: {}", fetchedDate);
        } catch (Exception e) {
            log.warn("Failed to retrieve and set accounting date from external service.", e);
        }
    }

}
