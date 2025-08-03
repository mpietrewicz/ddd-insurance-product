package pl.mpietrewicz.insurance.product.domain.repository;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepository;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;

import java.util.List;
import java.util.Optional;

@DomainRepository
public interface ContractRepository {

    Optional<Contract> load(ContractId contractId);

    void save(Contract contract);

    List<Contract> getAllContractsFor(InsuredId insuredId);

}