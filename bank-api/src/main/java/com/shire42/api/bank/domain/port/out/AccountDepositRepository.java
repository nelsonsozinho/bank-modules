package com.shire42.api.bank.domain.port.out;

import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.model.Transaction;

import java.math.BigDecimal;

public interface AccountDepositRepository {

    void makeDeposit(Account account, BigDecimal amount, TransactionType type);

}
