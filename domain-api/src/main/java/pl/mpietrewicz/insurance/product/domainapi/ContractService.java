package pl.mpietrewicz.insurance.product.domainapi;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.ContractNotFoundException;

import java.time.LocalDate;

public interface ContractService {

    /**
     * Checks whether the contract can be closed.
     *
     * @param contractId the ID of the contract to check
     * @return {@code true} if the contract can be closed, {@code false} otherwise
     * @throws ContractNotFoundException if the contract with the given ID does not exist
     */
    boolean canCloseContract(ContractId contractId);

    /**
     * Closes the contract if it is eligible for closure.
     *
     * @param contractId the identifier of the contract to close
     * @param endDate    the end date of the contract (currently not used in the implementation)
     * @throws ContractNotFoundException if a contract with the given ID does not exist
     */
    void closeContract(ContractId contractId, LocalDate endDate);

    /**
     * Checks whether the contract can be terminated. This method does not change the state of the contract.
     *
     * @param contractId the identifier of the contract to check
     * @return {@code true} if the contract can be terminated, {@code false} otherwise
     * @throws ContractNotFoundException if a contract with the given ID does not exist
     */
    boolean canTerminateContract(ContractId contractId);

    /**
     * Terminates the contract if it's eligible for termination.
     *
     * @param contractId      the ID of the contract to terminate
     * @param terminationDate the date of termination
     * @throws ContractNotFoundException if the contract with the given ID does not exist
     */
    void terminateContract(ContractId contractId, LocalDate terminationDate);

}