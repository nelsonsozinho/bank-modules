package com.shire42.api.score.service.impl;

import com.shire42.api.loan.model.Contract;
import com.shire42.api.loan.repository.ContractRepository;
import com.shire42.api.loan.service.dto.ContractDto;
import com.shire42.api.loan.service.exception.ContractAlreadyCompletedException;
import com.shire42.api.loan.service.exception.ContractNotFoundException;
import com.shire42.api.loan.service.impl.ContractServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContractServiceTest {

    @Mock
    private ContractRepository contractRepository;

    @InjectMocks
    private ContractServiceImpl contractService;


    @Test
    public void saveContractSuccessfully() {
        Contract contract = newContract();
        Optional<Contract> optionalContract = Optional.of(contract);

        when(contractRepository.save(any(Contract.class))).thenReturn(contract);
        when(contractRepository.findById(any())).thenReturn(optionalContract);

        ContractDto savedContract = contractService.finishContract(1L);

        assertNotNull(contract);
        assertEquals(Long.valueOf(1), savedContract.getId());
    }

    @Test
    public void saveContractThrowsException() {
        when(contractRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ContractNotFoundException.class,  () -> contractService.finishContract(1L));
        verify(contractRepository,never()).save(any(Contract.class));
    }

    @Test
    void saveContractThrowsExceptionWhenAlreadyCompleted() {
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setCompleted(true);

        when(contractRepository.findById(anyLong())).thenReturn(Optional.of(contract));

        assertThrows(ContractAlreadyCompletedException.class, () -> contractService.finishContract(1L));
        verify(contractRepository, never()).save(any(Contract.class));
    }

    private Contract newContract() {
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setBancAccountNumber("123456");
        contract.setIsAssign(false);
        contract.setCompleted(false);
        return contract;
    }

}
