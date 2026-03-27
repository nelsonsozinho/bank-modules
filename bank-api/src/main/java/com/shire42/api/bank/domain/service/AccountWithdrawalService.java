package com.shire42.api.bank.domain.service;

import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.port.in.AccountWithdrawalUseCase;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;
import com.shire42.api.bank.domain.port.out.AccountWithdrawalRepository;
import com.shire42.api.bank.domain.exceptions.BankAccountsNotFoundException;
import com.shire42.api.bank.domain.exceptions.InsuficientFoundsException;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountWithdrawalService implements AccountWithdrawalUseCase {

    private final AccountWithdrawalRepository accountWithdrawalRepository;

    private final AccountSearchRepository accountSearchRepository;


    public AccountWithdrawalService(final AccountWithdrawalRepository accountWithdrawalRepository,
                                     final AccountSearchRepository accountSearchRepository) {
        this.accountWithdrawalRepository = accountWithdrawalRepository;
        this.accountSearchRepository = accountSearchRepository;
    }

    @Override
    public void makeWithdrawal(String clientId, String accountNumber, BigDecimal amount) {
        Account account = accountSearchRepository.findAccountByNumber(accountNumber);
        validateBankAccount(account, accountNumber);

        final BigDecimal accountAmount = new BigDecimal(account.getBalance()).subtract(amount);

        validateTransaction(account, amount);

        account.setBalance(accountAmount.doubleValue());
        accountWithdrawalRepository.makeWithdrawal(account, clientId, accountNumber, amount);
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
