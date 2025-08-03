package pl.mpietrewicz.insurance.product.webapi.dto.assembler.impl;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.product.readmodel.model.ContractReadModel;
import pl.mpietrewicz.insurance.product.webapi.dto.assembler.ContractModelAssemblerInter;
import pl.mpietrewicz.insurance.product.webapi.dto.response.ContractModel;

import java.util.List;

@Component
public class ContractModelAssemblerImpl implements ContractModelAssemblerInter,
        RepresentationModelAssembler<ContractReadModel, ContractModel> {

    @Override
    public ContractModel toModel(ContractReadModel entity) {
        return ContractModel.builder()
                .id(entity.getAggregateId())
                .insuredId(entity.getInsuredId())
                .build();
    }

    @Override
    public CollectionModel<ContractModel> toModel(List<ContractReadModel> entities) {
        return CollectionModel.of(entities.stream()
                .map(this::toModel)
                .toList());
    }

}