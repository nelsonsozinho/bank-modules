package com.shire42.api.bank.domain.port.out;

import com.shire42.api.bank.domain.model.Account;

import java.math.BigDecimal;

public interface AccountWithdrawalRepository {

    void makeWithdrawal(Account account, String clientId, String accountNumber, BigDecimal amount);

}
