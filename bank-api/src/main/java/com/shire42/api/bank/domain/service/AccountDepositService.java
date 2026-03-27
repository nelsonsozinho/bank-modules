package com.shire42.api.bank.domain.service;

import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.model.Transaction;
import com.shire42.api.bank.domain.port.in.AccountDepositUseCase;
import com.shire42.api.bank.domain.port.out.AccountDepositRepository;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;
import com.shire42.api.bank.domain.exceptions.BankAccountsNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        Transaction transaction = prepareWithdrawalTransaction(account, Long.getLong(clientId), amount);
        accountDepositRepository.makeDeposit(account, amount, type, transaction);
    }

    private static void validateBankAccount(final Account account, final String bankAccountNumber) {
        if(Objects.isNull(account)) {
            throw new BankAccountsNotFoundException(String.format("Account %s was not found!", bankAccountNumber));
        }
    }

    private Transaction prepareWithdrawalTransaction(
            final Account sourceAccount,
            final Long clientId,
            final BigDecimal amount) {
        return Transaction.builder()
                .amount(amount.doubleValue())
                .transactionType(TransactionType.WITHDRAWAL.name())
                .clientId(clientId)
                .sourceAccountNumber(sourceAccount.getNumber())
                .date(LocalDate.now())
                .build();


    }

}
