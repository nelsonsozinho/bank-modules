package com.shire42.api.loan.service.impl;

import com.shire42.api.loan.client.async.EventService;
import com.shire42.api.loan.client.async.dto.LoanContractDTO;
import com.shire42.api.loan.model.Contract;
import com.shire42.api.loan.model.Simulation;
import com.shire42.api.loan.repository.ContractRepository;
import com.shire42.api.loan.repository.SimulationRepository;
import com.shire42.api.loan.service.ContractService;
import com.shire42.api.loan.service.dto.ContractDto;
import com.shire42.api.loan.service.dto.SimulationDto;
import com.shire42.api.loan.service.exception.ContractAlreadyCompletedException;
import com.shire42.api.loan.service.exception.ContractNotFoundException;
import com.shire42.api.loan.service.exception.SimulationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    private final SimulationRepository simulationRepository;

    private final EventService eventService;

    @Override
    public List<Contract> listAllActiveContractsByUser(final String cpf) {
        return contractRepository.findContractsActivatedYet(cpf);
    }

    @Override
    @Transactional
    public ContractDto dealContract(Long contractId, Long simulationId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() ->
                new ContractNotFoundException(String.format("Contract with ID %s not found", contractId)));
        Simulation simulation = contract.getSimulations().stream()
                .filter(s -> Objects.equals(s.getId(), simulationId))
                .findFirst()
                .orElseThrow(() -> new SimulationNotFoundException(String.format("Simulation with %s id not found", simulationId)));

        if(contract.getIsAssign())
            throw new ContractNotFoundException(String.format("Contract with id %s was already assign", contract.getId()));

        contract.setIsAssign(true);
        simulation.setIsEffective(true);

        LoanContractDTO dto = LoanContractDTO.builder()
            .bancAccountNumber(contract.getBancAccountNumber())
            .clientId(contract.getClient().getId())
            .contractId(contractId)
            .productDescription(contract.getProduct().getDescription())
            .instalmentSize(simulation.getInstallmentSize())
            .productId(contract.getProduct().getId())
            .loanValue(simulation.getTotal())
            .text("Loan")
        .build();


        contractRepository.save(contract);
        simulationRepository.save(simulation);
        eventService.sendEvent(dto);

        return ContractDto.builder()
                .bankAccountNumber(contract.getBancAccountNumber())
                .id(contract.getId())
                .isAssigned(contract.getIsAssign())
                .isCompleted(contract.getCompleted())
                .simulationDto(SimulationDto.builder()
                        .instalments(simulation.getInstallmentSize())
                        .bancAccountNumber(contract.getBancAccountNumber())
                        .id(simulation.getId())
                        .build())
                .build();
    }

    @Override
    @Transactional
    public ContractDto finishContract(final Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() ->
                new ContractNotFoundException(String.format("Contract with ID %s not found", contractId)));
        if(contract.getCompleted()) {
            throw new ContractAlreadyCompletedException(String.format("Contract with id %s is already completed", contract.getId()));
        }

        contract.setCompleted(true);
        contractRepository.save(contract);

        return ContractDto.builder()
                .bankAccountNumber(contract.getBancAccountNumber())
                .id(contract.getId())
                .isAssigned(contract.getIsAssign())
                .isCompleted(contract.getCompleted())
                .build();
    }

}
