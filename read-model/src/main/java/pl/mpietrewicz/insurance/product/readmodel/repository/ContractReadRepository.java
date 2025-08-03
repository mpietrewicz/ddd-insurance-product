package pl.mpietrewicz.insurance.product.readmodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mpietrewicz.insurance.ddd.annotations.application.Reader;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.readmodel.model.ContractReadModel;

import java.util.List;
import java.util.Optional;

@Reader
public interface ContractReadRepository extends JpaRepository<Contract, Long> {

    Optional<ContractReadModel> findByAggregateId(ContractId contractId);

    List<ContractReadModel> findByInsuredId(InsuredId insuredId);

    List<ContractReadModel> findAllProjectedBy();

}
