package pl.mpietrewicz.insurance.product.repository.impl;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepositoryImpl;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.GenericJpaRepository;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.repository.ContractRepository;
import pl.mpietrewicz.insurance.product.repository.ContractJpaRepository;

import java.util.List;

@DomainRepositoryImpl
@RequiredArgsConstructor
public class ContractRepositoryImpl extends GenericJpaRepository<Contract, ContractId> implements ContractRepository {

    private final ContractJpaRepository contractJpaRepository;

    @Override
    public List<Contract> getAllContractsFor(InsuredId insuredId) {
        return contractJpaRepository.findByInsuredId(insuredId);
    }

}