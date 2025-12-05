package com.shire42.api.bank.domain.service;

import com.shire42.api.bank.domain.model.rest.out.AccountOutRest;
import com.shire42.api.bank.domain.service.transaction.TransactionType;

import java.math.BigDecimal;

public interface AccountService {

    AccountOutRest getAccountByAccountNumber(final String accountNumber);

    void makeDeposit(final String clientId, final String accountNumber, final BigDecimal amount, final TransactionType type);

    void makeWithdrawal(final String clientId, final String accountNumber, final BigDecimal amount);

    void makeTransaction(final String sourceAccountNumber, final String targetAccountNumber, final BigDecimal value);

}
