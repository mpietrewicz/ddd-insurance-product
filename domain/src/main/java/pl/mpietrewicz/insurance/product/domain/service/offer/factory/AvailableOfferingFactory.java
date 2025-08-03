package pl.mpietrewicz.insurance.product.domain.service.offer.factory;

import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;

import java.time.LocalDate;
import java.util.List;

public interface AvailableOfferingFactory {

    AvailableOffering create(Product product, List<Contract> contracts, LocalDate offerStartDate);

}
