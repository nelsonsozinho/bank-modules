package com.shire42.api.bank.domain.service;

import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.model.LoanContractEvent;
import com.shire42.api.bank.domain.port.in.LoanContractConsumerUseCase;
import com.shire42.api.bank.domain.port.out.AccountDepositRepository;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;

@Log4j2
public class LoanContractConsumerUseCaseService implements LoanContractConsumerUseCase {

    private final AccountDepositRepository accountDepositRepository;

    private final AccountSearchRepository accountSearchRepository;

    public LoanContractConsumerUseCaseService(
            final AccountDepositRepository accountDepositRepository,
            final AccountSearchRepository accountSearchRepository
            ) {
        this.accountDepositRepository = accountDepositRepository;
        this.accountSearchRepository = accountSearchRepository;
    }

    @Override
    public void process(LoanContractEvent event) {
        log.info("Loan contract event received: {}", event.getContractId());
        final Account account = accountSearchRepository.findAccountByNumber(event.getBancAccountNumber());
        accountDepositRepository.makeDeposit(account, new BigDecimal(event.getLoanValue()), TransactionType.DEPOSIT);
    }

}
