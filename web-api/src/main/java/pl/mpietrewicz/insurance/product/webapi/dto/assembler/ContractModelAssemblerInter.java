package pl.mpietrewicz.insurance.product.webapi.dto.assembler;

import org.springframework.hateoas.CollectionModel;
import pl.mpietrewicz.insurance.product.readmodel.model.ContractReadModel;
import pl.mpietrewicz.insurance.product.webapi.dto.response.ContractModel;

import java.util.List;

public interface ContractModelAssemblerInter {

    ContractModel toModel(ContractReadModel entity);

    CollectionModel<ContractModel> toModel(List<ContractReadModel> entities);

}
