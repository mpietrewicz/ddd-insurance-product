package pl.mpietrewicz.insurance.product.repository.init.service.impl;

import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.product.repository.init.service.ExternalAccountingClient;

import java.time.LocalDate;

@Service
public class ExternalAccountingClientImpl implements ExternalAccountingClient {

    @Override
    public LocalDate fetchAccountingDate() {
        return LocalDate.now(); // todo: fetch accounting date from balance microservice
    }

}
