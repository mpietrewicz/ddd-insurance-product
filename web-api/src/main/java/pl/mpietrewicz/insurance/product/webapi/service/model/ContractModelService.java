package pl.mpietrewicz.insurance.product.webapi.service.model;

import org.springframework.hateoas.CollectionModel;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.ContractNotFoundException;
import pl.mpietrewicz.insurance.product.webapi.dto.response.ContractModel;

public interface ContractModelService {

    /**
     * Retrieves a contract model by its unique identifier.
     *
     * @param contractId the unique identifier of the contract, must not be null
     * @return the ContractModel corresponding to the provided identifier, never null
     * @throws ContractNotFoundException if no contract exists with the given identifier
     * @throws IllegalArgumentException if contractId is null
     */
    ContractModel getById(ContractId contractId);

    /**
     * Retrieves a collection of ContractModel objects associated with the specified insured ID.
     *
     * @param insuredId the unique identifier of the insured individual, must not be null
     * @return a CollectionModel containing all ContractModel objects related to the provided insured ID, never null
     * @throws IllegalArgumentException if insuredId is null
     */
    CollectionModel<ContractModel> getByInsuredId(InsuredId insuredId);

}
