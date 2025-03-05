package pl.mpietrewicz.insurance.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepositoryImpl;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;

import java.util.List;

@DomainRepositoryImpl
public interface ContractJpaRepository extends JpaRepository<Contract, ContractId> {

    List<Contract> findByInsuredId(InsuredId insuredId);

}
