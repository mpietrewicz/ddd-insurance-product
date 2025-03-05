package pl.mpietrewicz.insurance.product.webapi.service.model.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.OfferNotFoundException;
import pl.mpietrewicz.insurance.product.readmodel.model.OfferReadModel;
import pl.mpietrewicz.insurance.product.readmodel.repository.OfferReadRepository;
import pl.mpietrewicz.insurance.product.webapi.dto.assembler.impl.OfferModelAssemblerImpl;
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferModel;
import pl.mpietrewicz.insurance.product.webapi.service.model.OfferModelService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OfferModelServiceImpl implements OfferModelService {

    private final OfferReadRepository offerReadRepository;

    private final OfferModelAssemblerImpl offerModelAssembler;

    @Override
    public OfferModel getBy(OfferId offerId) {
        if (offerId == null) {
            throw new IllegalArgumentException("offerId cannot be null");
        }

        OfferReadModel offerReadModel = offerReadRepository.findByAggregateId(offerId)
                .orElseThrow(() -> new OfferNotFoundException(offerId));
        return offerModelAssembler.toModel(offerReadModel);
    }

    @Override
    public CollectionModel<OfferModel> getByApplicantId(ApplicantId applicantId) {
        if (applicantId == null) {
            throw new IllegalArgumentException("applicantId cannot be null");
        }

        List<OfferReadModel> offerReadModels = offerReadRepository.findByApplicantId(applicantId);
        return offerModelAssembler.toModel(offerReadModels);
    }

}
