package pl.mpietrewicz.insurance.product.webapi.dto.converter;

import org.springframework.hateoas.CollectionModel;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;
import pl.mpietrewicz.insurance.product.webapi.dto.response.AvailableOfferingModel;

import java.util.List;

public interface AvailableOfferingConverter {

    AvailableOfferingModel convert(AvailableOffering availableOffering);

    CollectionModel<AvailableOfferingModel> convert(List<AvailableOffering> availableOfferings);

}
