package com.shire42.api.bank.adapter.out.persistence.impl;

import com.shire42.api.bank.adapter.out.persistence.model.AccountEntity;
import com.shire42.api.bank.adapter.out.persistence.model.TransactionEntity;
import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;
import com.shire42.api.bank.adapter.out.persistence.repository.AccountRepository;
import com.shire42.api.bank.adapter.out.persistence.repository.TransactionRepository;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.port.out.AccountWithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AccountWithdrawalAdapter implements AccountWithdrawalRepository {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    @Override
    public void makeWithdrawal(Account account, String clientId, String accountNumber, BigDecimal amount) {
        AccountEntity accountEntity = accountRepository.findByNumber(account.getNumber());
        accountEntity.setBalance(account.getBalance());
        accountRepository.save(accountEntity);
        registerWithdrawalTransaction(account.getNumber(), account.getId(), amount);
    }

    private void registerWithdrawalTransaction(final String accountNumber,
                                            final Long clientId,
                                            final BigDecimal amount) {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .transactionType(TransactionType.WITHDRAWAL.name())
                .amount(amount.doubleValue())
                .sourceAccountNumber(accountNumber)
                .targetAccountNumber(accountNumber)
                .date(LocalDate.now())
                .clientId(clientId)
                .build();
        transactionRepository.save(transactionEntity);
    }

}
