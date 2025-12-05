package com.shire42.api.bank.domain.service.impl;

import com.shire42.api.bank.client.BankClient;
import com.shire42.api.bank.client.dto.Address;
import com.shire42.api.bank.client.dto.Client;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.model.Transaction;
import com.shire42.api.bank.domain.model.rest.in.ClientAccountRest;
import com.shire42.api.bank.domain.model.rest.out.AccountOutRest;
import com.shire42.api.bank.domain.model.rest.out.ClientAccountOutRest;
import com.shire42.api.bank.domain.repository.AccountRepository;
import com.shire42.api.bank.domain.repository.TransactionRepository;
import com.shire42.api.bank.domain.service.AccountService;
import com.shire42.api.bank.domain.service.exceptions.BankAccountsNotFoundException;
import com.shire42.api.bank.domain.service.exceptions.ClientNotFountException;
import com.shire42.api.bank.domain.service.exceptions.InsuficientFoundsException;
import com.shire42.api.bank.domain.service.transaction.TransactionType;
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

    private final BankClient bankClient;

    @Override
    @Cacheable(value = "accountCache", key = "#accountNumber")
    public AccountOutRest getAccountByAccountNumber(final String accountNumber) {
        Account account = repository.findByNumber(accountNumber);
        return  AccountOutRest.builder()
                .accountId(account.getId())
                .balance(BigDecimal.valueOf(account.getBalance()))
                .number(account.getNumber())
                .build();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void makeTransaction(final String sourceAccountNumber, final String targetAccountNumber, final BigDecimal value) {
        Account sourceAccount = repository.findByNumber(sourceAccountNumber);
        Account targetAccount = repository.findByNumber(targetAccountNumber);

        validateBankAccount(sourceAccount, sourceAccountNumber);
        validateBankAccount(targetAccount, targetAccountNumber);
        validateTransaction(sourceAccount, value);

        validateClient(sourceAccount);
        validateClient(targetAccount);

        var sourceBalance = new BigDecimal(sourceAccount.getBalance());
        var targetBalance = new BigDecimal(targetAccount.getBalance());

        sourceBalance = sourceBalance.subtract(value);
        targetBalance = targetBalance.add(value);

        sourceAccount.setBalance(sourceBalance.doubleValue());
        targetAccount.setBalance(targetBalance.doubleValue());

        registerTransferTransaction(sourceAccount, targetAccount, sourceAccount.getClientId(), value);
        repository.save(sourceAccount);
        repository.save(targetAccount);
    }

    @Override
    public ClientAccountOutRest createBankAccount(ClientAccountRest clientAccountRest) {
        Client client = Client.builder()
                .name(clientAccountRest.name())
                .email(clientAccountRest.email())
                .cpf(clientAccountRest.cpf())
                .rg(clientAccountRest.rg())
                .build();

        Address address = Address.builder()
                .street(clientAccountRest.address().street())
                .number(clientAccountRest.address().number())
                .city(clientAccountRest.address().city())
                .state(clientAccountRest.address().state())
                .zipCode(clientAccountRest.address().zipCode())
                .build();

        Client newClient = bankClient.saveNewClient(client);

        return null;
    }

    @Transactional
    public void makeDeposit(final String clientId, final String accountNumber, final BigDecimal amount, TransactionType type) {
        final Account account = repository.findByNumber(accountNumber);
        validateBankAccount(account, accountNumber);

        final BigDecimal accountAmount = new BigDecimal(account.getBalance()).add(amount);

        account.setBalance(accountAmount.doubleValue());
        registerDepositTransaction(account, Long.parseLong(clientId), amount, type);
        repository.save(account);
    }

    @Transactional
    public void makeWithdrawal(final String clientId, final String accountNumber, final BigDecimal amount) {
        final Account account = repository.findByNumber(accountNumber);
        validateBankAccount(account, accountNumber);

        final BigDecimal accountAmount = new BigDecimal(account.getBalance()).subtract(amount);

        validateTransaction(account, amount);

        account.setBalance(accountAmount.doubleValue());
        registerWithdrawalTransaction(account, Long.parseLong(clientId), amount);
        repository.save(account);
    }

    private void registerTransferTransaction(final Account sourceAccount,
                                             final Account targetAccount,
                                             Long clientId,
                                             BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount.doubleValue());
        transaction.setTransactionType(TransactionType.TRANSFER.name());
        transaction.setClientId(clientId);
        transaction.setSourceAccountNumber(sourceAccount.getNumber());
        transaction.setTargetAccountNumber(targetAccount.getNumber());
        transaction.setDate(LocalDate.now());
        transactionRepository.save(transaction);
    }

    private void registerWithdrawalTransaction(final Account sourceAccount,
                                             Long clientId,
                                             BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount.doubleValue());
        transaction.setTransactionType(TransactionType.WITHDRAWAL.name());
        transaction.setClientId(clientId);
        transaction.setSourceAccountNumber(sourceAccount.getNumber());
        transaction.setDate(LocalDate.now());
        transactionRepository.save(transaction);
    }

    private void registerDepositTransaction(final Account sourceAccount,
                                            final Long clientId,
                                            final BigDecimal amount,
                                            final TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount.doubleValue());
        transaction.setTransactionType(type.name());
        transaction.setClientId(clientId);
        transaction.setSourceAccountNumber(sourceAccount.getNumber());
        transaction.setTargetAccountNumber(sourceAccount.getNumber());
        transaction.setDate(LocalDate.now());
        transactionRepository.save(transaction);
    }

    private void validateBankAccount(final Account account, final String bankAccountNumber) {
        if(Objects.isNull(account)) {
            throw new BankAccountsNotFoundException(String.format("Account %s was not found!", bankAccountNumber));
        }
    }

    private void validateTransaction(final Account sourceAccount, BigDecimal value) {
        if(new BigDecimal(sourceAccount.getBalance()).subtract(value).longValue() < 0) {
            throw new InsuficientFoundsException( String.format("Account %s has no enought founds to complete the transaction", sourceAccount.getNumber()));
        }
    }

    private void validateClient(final Account account) {
        Client client = bankClient.getClientById(account.getClientId());
        if(Objects.isNull(client)) {
            throw new ClientNotFountException(String.format("Client %d was not found!", account.getClientId()));
        }
    }

}
