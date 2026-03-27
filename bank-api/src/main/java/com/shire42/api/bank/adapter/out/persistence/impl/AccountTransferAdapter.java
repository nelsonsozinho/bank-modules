package com.shire42.api.bank.adapter.out.persistence.impl;

import com.shire42.api.bank.adapter.out.persistence.model.AccountEntity;
import com.shire42.api.bank.adapter.out.persistence.model.TransactionEntity;
import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;
import com.shire42.api.bank.adapter.out.persistence.repository.AccountRepository;
import com.shire42.api.bank.adapter.out.persistence.repository.TransactionRepository;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.port.out.AccountTransferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AccountTransferAdapter implements AccountTransferRepository {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    @Transactional
    @Override
    public void makeTransaction(Account sourceAccount, Account targetAccount, BigDecimal amount) {
        AccountEntity entitySourceAccount = accountRepository.findByNumber(sourceAccount.getNumber());
        AccountEntity entityTargetAccount = accountRepository.findByNumber(targetAccount.getNumber());

        entitySourceAccount.setBalance(sourceAccount.getBalance());
        entityTargetAccount.setBalance(sourceAccount.getBalance());

        registerTransferTransaction(entitySourceAccount, entityTargetAccount, sourceAccount.getClient().getId(), amount);
        accountRepository.save(entitySourceAccount);
        accountRepository.save(entityTargetAccount);
    }

    private void registerTransferTransaction(final AccountEntity sourceAccount,
                                             final AccountEntity targetAccount,
                                             Long clientId,
                                             BigDecimal amount) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(amount.doubleValue());
        transaction.setTransactionType(TransactionType.TRANSFER.name());
        transaction.setClientId(clientId);
        transaction.setSourceAccountNumber(sourceAccount.getNumber());
        transaction.setTargetAccountNumber(targetAccount.getNumber());
        transaction.setDate(LocalDate.now());
        transactionRepository.save(transaction);
    }

}
