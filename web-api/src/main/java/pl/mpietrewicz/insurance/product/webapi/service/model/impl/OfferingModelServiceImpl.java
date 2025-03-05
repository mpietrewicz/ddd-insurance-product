package pl.mpietrewicz.insurance.product.webapi.service.model.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.OfferNotFoundException;
import pl.mpietrewicz.insurance.product.readmodel.model.OfferReadModel;
import pl.mpietrewicz.insurance.product.readmodel.model.OfferingReadModel;
import pl.mpietrewicz.insurance.product.readmodel.repository.OfferReadRepository;
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferingModel;
import pl.mpietrewicz.insurance.product.webapi.dto.assembler.impl.OfferingModelAssemblerImpl;
import pl.mpietrewicz.insurance.product.webapi.service.model.OfferingModelService;

@Component
@RequiredArgsConstructor
public class OfferingModelServiceImpl implements OfferingModelService {

    private final OfferReadRepository offerReadRepository;

    private final OfferingModelAssemblerImpl offeringModelAssembler;

    @Override
    public CollectionModel<OfferingModel> getBy(OfferId offerId) {
        if (offerId == null) {
            throw new IllegalArgumentException("offerId cannot be null");
        }

        OfferReadModel offerReadModel = offerReadRepository.findByAggregateId(offerId)
                .orElseThrow(() -> new OfferNotFoundException(offerId));
        return offeringModelAssembler.toModel(offerReadModel.getOfferings());
    }

    @Override
    public OfferingModel getBy(OfferId offerId, Long offeringId) {
        if (offerId == null) {
            throw new IllegalArgumentException("offerId cannot be null");
        } else if (offeringId == null) {
            throw new IllegalArgumentException("offeringId cannot be null");
        }

        OfferReadModel offerReadModel = offerReadRepository.findByAggregateId(offerId)
                .orElseThrow(() -> new OfferNotFoundException(offerId));
        OfferingReadModel offeringReadModel = offerReadModel.getOfferings().stream()
                .filter(offering -> offering.getEntityId().equals(offeringId))
                .findAny()
                .orElseThrow();

        return offeringModelAssembler.toModel(offeringReadModel);
    }

}
