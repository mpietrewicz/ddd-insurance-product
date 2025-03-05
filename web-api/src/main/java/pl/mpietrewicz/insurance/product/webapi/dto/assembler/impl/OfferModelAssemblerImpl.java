package pl.mpietrewicz.insurance.product.webapi.dto.assembler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.product.readmodel.model.OfferReadModel;
import pl.mpietrewicz.insurance.product.webapi.dto.assembler.OfferModelAssembler;
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferModel;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OfferModelAssemblerImpl implements OfferModelAssembler,
        RepresentationModelAssembler<OfferReadModel, OfferModel> {

    private final OfferingModelAssemblerImpl offeringModelAssembler;

    @Override
    public OfferModel toModel(OfferReadModel entity) {
        return OfferModel.builder()
                .id(entity.getAggregateId())
                .applicantId(entity.getApplicantId())
                .startDate(entity.getStartDate())
                .offerings(offeringModelAssembler.toModel(entity.getOfferings()))
                .build();
    }

    @Override
    public CollectionModel<OfferModel> toModel(List<OfferReadModel> entities) {
        return CollectionModel.of(entities.stream()
                .map(this::toModel)
                .toList());
    }

}