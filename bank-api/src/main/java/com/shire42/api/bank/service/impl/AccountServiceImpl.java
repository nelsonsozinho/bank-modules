package com.shire42.api.bank.service.impl;

import com.shire42.api.bank.adapter.out.persistence.model.AccountEntity;
import com.shire42.api.bank.adapter.out.persistence.model.TransactionEntity;
import com.shire42.api.bank.adapter.out.dto.AccountOutRest;
import com.shire42.api.bank.adapter.out.persistence.repository.AccountRepository;
import com.shire42.api.bank.adapter.out.persistence.repository.TransactionRepository;
import com.shire42.api.bank.service.AccountService;
import com.shire42.api.bank.domain.exceptions.BankAccountsNotFoundException;
import com.shire42.api.bank.domain.exceptions.InsuficientFoundsException;
import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    private final TransactionRepository transactionRepository;

    @Override
    @Cacheable(value = "accountCache", key = "#accountNumber")
    public AccountOutRest getAccountByAccountNumber(final String accountNumber) {
        AccountEntity account = repository.findByNumber(accountNumber);
        return  AccountOutRest.builder()
                .accountId(account.getId())
                .balance(BigDecimal.valueOf(account.getBalance()))
                .number(account.getNumber())
                .build();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void makeTransaction(final String sourceAccountNumber, final String targetAccountNumber, final BigDecimal value) {
        AccountEntity sourceAccount = repository.findByNumber(sourceAccountNumber);
        AccountEntity targetAccount = repository.findByNumber(targetAccountNumber);

        validateBankAccount(sourceAccount, sourceAccountNumber);
        validateBankAccount(targetAccount, targetAccountNumber);
        validateTransaction(sourceAccount, value);

        var sourceBalance = new BigDecimal(sourceAccount.getBalance());
        var targetBalance = new BigDecimal(targetAccount.getBalance());

        sourceBalance = sourceBalance.subtract(value);
        targetBalance = targetBalance.add(value);

        sourceAccount.setBalance(sourceBalance.doubleValue());
        targetAccount.setBalance(targetBalance.doubleValue());

        registerTransferTransaction(sourceAccount, targetAccount, sourceAccount.getClient().getId(), value);
        repository.save(sourceAccount);
        repository.save(targetAccount);
    }

    @Transactional
    public void makeDeposit(final String clientId, final String accountNumber, final BigDecimal amount, TransactionType type) {
        final AccountEntity account = repository.findByNumber(accountNumber);
        validateBankAccount(account, accountNumber);

        final BigDecimal accountAmount = new BigDecimal(account.getBalance()).add(amount);

        account.setBalance(accountAmount.doubleValue());
        registerDepositTransaction(account, Long.parseLong(clientId), amount, type);
        repository.save(account);
    }

    @Transactional
    public void makeWithdrawal(final String clientId, final String accountNumber, final BigDecimal amount) {
        final AccountEntity account = repository.findByNumber(accountNumber);
        validateBankAccount(account, accountNumber);

        final BigDecimal accountAmount = new BigDecimal(account.getBalance()).subtract(amount);

        validateTransaction(account, amount);

        account.setBalance(accountAmount.doubleValue());
        registerWithdrawalTransaction(account, Long.parseLong(clientId), amount);
        repository.save(account);
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

    private void registerWithdrawalTransaction(final AccountEntity sourceAccount,
                                             Long clientId,
                                             BigDecimal amount) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(amount.doubleValue());
        transaction.setTransactionType(TransactionType.WITHDRAWAL.name());
        transaction.setClientId(clientId);
        transaction.setSourceAccountNumber(sourceAccount.getNumber());
        transaction.setDate(LocalDate.now());
        transactionRepository.save(transaction);
    }

    private void registerDepositTransaction(final AccountEntity sourceAccount,
                                            final Long clientId,
                                            final BigDecimal amount,
                                            final TransactionType type) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(amount.doubleValue());
        transaction.setTransactionType(type.name());
        transaction.setClientId(clientId);
        transaction.setSourceAccountNumber(sourceAccount.getNumber());
        transaction.setTargetAccountNumber(sourceAccount.getNumber());
        transaction.setDate(LocalDate.now());
        transactionRepository.save(transaction);
    }

    private void validateBankAccount(final AccountEntity account, final String bankAccountNumber) {
        if(Objects.isNull(account)) {
            throw new BankAccountsNotFoundException(String.format("Account %s was not found!", bankAccountNumber));
        }
    }

    private void validateTransaction(final AccountEntity sourceAccount, BigDecimal value) {
        if(new BigDecimal(sourceAccount.getBalance()).subtract(value).longValue() < 0) {
            throw new InsuficientFoundsException( String.format("Account %s has no enought founds to complete the transaction", sourceAccount.getNumber()));
        }
    }

}
