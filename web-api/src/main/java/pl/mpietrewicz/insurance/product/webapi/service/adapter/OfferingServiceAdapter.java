package pl.mpietrewicz.insurance.product.webapi.service.adapter;

import org.springframework.hateoas.CollectionModel;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingKey;
import pl.mpietrewicz.insurance.product.webapi.dto.request.AddOfferingRequest;
import pl.mpietrewicz.insurance.product.webapi.dto.response.AvailableOfferingModel;

public interface OfferingServiceAdapter {

    CollectionModel<AvailableOfferingModel> getAvailableOfferings(OfferId offerId);

    OfferingKey addOffering(OfferId offerId, AddOfferingRequest addOfferingRequest);

}
