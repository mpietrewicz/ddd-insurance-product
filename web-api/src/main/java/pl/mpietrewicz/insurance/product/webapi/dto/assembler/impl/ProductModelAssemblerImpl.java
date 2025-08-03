package pl.mpietrewicz.insurance.product.webapi.dto.assembler.impl;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.product.readmodel.model.ProductReadModel;
import pl.mpietrewicz.insurance.product.webapi.dto.assembler.ProductModelAssembler;
import pl.mpietrewicz.insurance.product.webapi.dto.response.ProductModel;

import java.util.List;

@Component
public class ProductModelAssemblerImpl implements ProductModelAssembler,
        RepresentationModelAssembler<ProductReadModel, ProductModel> {

    @Override
    public ProductModel toModel(ProductReadModel entity) {
        return ProductModel.builder()
                .id(entity.getAggregateId())
                .name(entity.getName())
                .premium(entity.getPremium())
                .build();
    }

    @Override
    public CollectionModel<ProductModel> toModel(List<ProductReadModel> entities) {
        return CollectionModel.of(entities.stream()
                .map(this::toModel)
                .toList());
    }

}