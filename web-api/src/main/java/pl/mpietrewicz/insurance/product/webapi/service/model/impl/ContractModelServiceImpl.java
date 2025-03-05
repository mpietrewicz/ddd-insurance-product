package pl.mpietrewicz.insurance.product.webapi.service.model.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.ContractNotFoundException;
import pl.mpietrewicz.insurance.product.readmodel.model.ContractReadModel;
import pl.mpietrewicz.insurance.product.readmodel.repository.ContractReadRepository;
import pl.mpietrewicz.insurance.product.webapi.dto.assembler.impl.ContractModelAssemblerImpl;
import pl.mpietrewicz.insurance.product.webapi.dto.response.ContractModel;
import pl.mpietrewicz.insurance.product.webapi.service.model.ContractModelService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContractModelServiceImpl implements ContractModelService {

    private final ContractReadRepository contractReadRepository;

    private final ContractModelAssemblerImpl contractModelAssembler;

    @Override
    public ContractModel getById(ContractId contractId) {
        if (contractId == null) {
            throw new IllegalArgumentException("contractId cannot be null");
        }

        ContractReadModel contractReadModel = contractReadRepository.findByAggregateId(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
        return contractModelAssembler.toModel(contractReadModel);
    }

    @Override
    public CollectionModel<ContractModel> getByInsuredId(InsuredId insuredId) {
        if (insuredId == null) {
            throw new IllegalArgumentException("insuredId cannot be null");
        }

        List<ContractReadModel> contractReadModels = contractReadRepository.findByInsuredId(insuredId);
        return contractModelAssembler.toModel(contractReadModels);
    }

}
