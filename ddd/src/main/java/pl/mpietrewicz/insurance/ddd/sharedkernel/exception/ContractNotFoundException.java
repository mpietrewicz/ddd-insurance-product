package pl.mpietrewicz.insurance.ddd.sharedkernel.exception;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;

import java.util.NoSuchElementException;

public class ContractNotFoundException extends NoSuchElementException {

    public ContractNotFoundException(ContractId contractId) {
        super("Contract not found for id: " + contractId);
    }

}
