package pl.mpietrewicz.insurance.product.domain.service.offer.factory;

import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.AvailableOffering;

public interface AvailableOfferingFactory {

    AvailableOffering create(Product product);

}
