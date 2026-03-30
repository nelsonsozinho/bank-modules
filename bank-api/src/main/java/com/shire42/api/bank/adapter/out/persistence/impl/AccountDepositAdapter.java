package com.shire42.api.bank.adapter.out.persistence.impl;

import com.shire42.api.bank.adapter.out.persistence.model.AccountEntity;
import com.shire42.api.bank.adapter.out.persistence.model.TransactionEntity;
import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;
import com.shire42.api.bank.adapter.out.persistence.repository.AccountRepository;
import com.shire42.api.bank.adapter.out.persistence.repository.TransactionRepository;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.port.out.AccountDepositRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class AccountDepositAdapter implements AccountDepositRepository {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void makeDeposit(Account account, BigDecimal amount, TransactionType type) {
        registerDepositTransaction(account.getNumber(), account.getId(), amount , type);
        accountRepository.save(convertToAccountEntity(account));
    }

    private void registerDepositTransaction(final String accountNumber,
                                            final Long clientId,
                                            final BigDecimal amount,
                                            final TransactionType type) {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .transactionType(type.name())
                .amount(amount.doubleValue())
                .sourceAccountNumber(accountNumber)
                .targetAccountNumber(accountNumber)
                .date(LocalDate.now())
                .clientId(clientId)
                .build();
        transactionRepository.save(transactionEntity);
    }

    private AccountEntity convertToAccountEntity(final Account account) {
        AccountEntity accountEntity = accountRepository.findByNumber(account.getNumber());
        accountEntity.setBalance(account.getBalance());
        return accountEntity;
    }
}
