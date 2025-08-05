package pl.mpietrewicz.insurance.product.webapi.dto.assembler.impl;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.product.readmodel.model.OfferingReadModel;
import pl.mpietrewicz.insurance.product.webapi.dto.assembler.OfferingModelAssembler;
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferingModel;

import java.util.List;

@Component
public class OfferingModelAssemblerImpl implements OfferingModelAssembler,
        RepresentationModelAssembler<OfferingReadModel, OfferingModel> {

    @Override
    public OfferingModel toModel(OfferingReadModel entity) {
        return OfferingModel.builder()
                .id(entity.getEntityId())
                .productId(entity.getProductId())
                .promotion(entity.getPromotionType())
                .build();
    }

    @Override
    public CollectionModel<OfferingModel> toModel(List<OfferingReadModel> entities) {
        return CollectionModel.of(entities.stream()
                .map(this::toModel)
                .toList());
    }

}