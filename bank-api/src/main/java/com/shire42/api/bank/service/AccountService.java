package com.shire42.api.bank.service;

import com.shire42.api.bank.adapter.out.dto.AccountOutRest;
import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;

import java.math.BigDecimal;

public interface AccountService {

    AccountOutRest getAccountByAccountNumber(final String accountNumber);

    void makeDeposit(final String clientId, final String accountNumber, final BigDecimal amount, final TransactionType type);

    void makeWithdrawal(final String clientId, final String accountNumber, final BigDecimal amount);

    void makeTransaction(final String sourceAccountNumber, final String targetAccountNumber, final BigDecimal value);

}
