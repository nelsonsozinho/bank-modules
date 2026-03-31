package com.shire42.api.bank.domain.service;

import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.model.LoanContractEvent;
import com.shire42.api.bank.domain.port.in.AccountDepositUseCase;
import com.shire42.api.bank.domain.port.in.LoanContractConsumerUseCase;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;

@Log4j2
public class LoanContractConsumerUseCaseService implements LoanContractConsumerUseCase {


    private final AccountSearchRepository accountSearchRepository;

    private final AccountDepositUseCase accountDepositUseCase;

    public LoanContractConsumerUseCaseService(
            final AccountDepositUseCase accountDepositUseCase,
            final AccountSearchRepository accountSearchRepository
            ) {
        this.accountDepositUseCase = accountDepositUseCase;
        this.accountSearchRepository = accountSearchRepository;
    }

    @Override
    public void process(LoanContractEvent event) {
        log.info("Loan contract event received: {}", event.getContractId());
        final Account account = accountSearchRepository.findAccountByNumber(event.getBancAccountNumber());
        accountDepositUseCase.makeDeposit(event.getClientId().toString(), account.getNumber(), new BigDecimal(event.getLoanValue()), TransactionType.LOAN);
    }

}
