package pl.mpietrewicz.insurance.product.repository.init.service;

import java.time.LocalDate;

public interface ExternalAccountingClient {

    LocalDate fetchAccountingDate();

}
