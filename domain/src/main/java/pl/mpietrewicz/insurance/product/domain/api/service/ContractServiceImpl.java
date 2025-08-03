package pl.mpietrewicz.insurance.product.domain.api.service;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.application.ApplicationService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.repository.ContractRepository;
import pl.mpietrewicz.insurance.product.domainapi.ContractService;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.ContractNotFoundException;

import java.time.LocalDate;

@ApplicationService
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    @Override
    public boolean canCloseContract(ContractId contractId) {
        Contract contract = contractRepository.load(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
        return contract.canClose();
    }

    @Override
    public void closeContract(ContractId contractId, LocalDate endDate) {
        Contract contract = contractRepository.load(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
        contract.close();
    }

    @Override
    public boolean canTerminateContract(ContractId contractId) {
        Contract contract = contractRepository.load(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
        return contract.canTerminate();
    }

    @Override
    public void terminateContract(ContractId contractId, LocalDate terminationDate) {
        Contract contract = contractRepository.load(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
        contract.terminate();
    }

}
