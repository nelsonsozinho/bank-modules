package com.shire42.api.bank.domain.service;

import com.shire42.api.bank.domain.exceptions.BankAccountsNotFoundException;
import com.shire42.api.bank.domain.exceptions.InsuficientFoundsException;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.port.in.AccountTransferUseCase;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;
import com.shire42.api.bank.domain.port.out.AccountTransferRepository;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountTransferService implements AccountTransferUseCase {

    private final AccountTransferRepository accountTraansferRepository;

    private final AccountSearchRepository accountSearchRepository;


    public AccountTransferService(final AccountTransferRepository accountTraansferRepository,
                                  final AccountSearchRepository accountSearchRepository) {
        this.accountTraansferRepository = accountTraansferRepository;
        this.accountSearchRepository = accountSearchRepository;
    }

    @Override
    public void transfer(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount) {
        Account sourceAccount = accountSearchRepository.findAccountByNumber(sourceAccountNumber);
        Account targetAccount = accountSearchRepository.findAccountByNumber(targetAccountNumber);

        validateBankAccount(sourceAccount, sourceAccountNumber);
        validateBankAccount(targetAccount, targetAccountNumber);
        validateTransaction(sourceAccount, amount);

        var sourceBalance = new BigDecimal(sourceAccount.getBalance());
        var targetBalance = new BigDecimal(targetAccount.getBalance());

        sourceBalance = sourceBalance.subtract(amount);
        targetBalance = targetBalance.add(amount);

        sourceAccount.setBalance(sourceBalance.doubleValue());
        targetAccount.setBalance(targetBalance.doubleValue());

        accountTraansferRepository.makeTransaction(sourceAccount, targetAccount, amount);
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

}
