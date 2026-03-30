package com.shire42.api.bank.domain.service;

import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;
import com.shire42.api.bank.domain.exceptions.BankAccountsNotFoundException;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.port.in.AccountDepositUseCase;
import com.shire42.api.bank.domain.port.out.AccountDepositRepository;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountDepositService implements AccountDepositUseCase {

    private final AccountDepositRepository accountDepositRepository;

    private final AccountSearchRepository accountSearchRepository;

    public AccountDepositService(
            AccountDepositRepository accountDepositRepository,
            AccountSearchRepository accountSearchRepository) {
        this.accountDepositRepository = accountDepositRepository;
        this.accountSearchRepository = accountSearchRepository;
    }

    @Override
    public void makeDeposit(String clientId, String accountNumber, BigDecimal amount, TransactionType type) {
        Account account = accountSearchRepository.findAccountByNumber(accountNumber);
        validateBankAccount(account, accountNumber);

        final BigDecimal accountAmount = new BigDecimal(account.getBalance()).add(amount);

        account.setBalance(accountAmount.doubleValue());
        accountDepositRepository.makeDeposit(account, amount, type);
    }

    private static void validateBankAccount(final Account account, final String bankAccountNumber) {
        if(Objects.isNull(account)) {
            throw new BankAccountsNotFoundException(String.format("Account %s was not found!", bankAccountNumber));
        }
    }

}
