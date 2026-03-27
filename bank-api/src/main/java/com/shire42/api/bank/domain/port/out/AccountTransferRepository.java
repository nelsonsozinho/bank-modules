package com.shire42.api.bank.domain.port.out;

import com.shire42.api.bank.domain.model.Account;

import java.math.BigDecimal;

public interface AccountTransferRepository {

    void makeTransaction(Account sourceAccountNumber, Account targetAccount, BigDecimal amount);
}
