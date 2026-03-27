package com.shire42.api.bank.domain.port.in;

import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;

import java.math.BigDecimal;

public interface AccountDepositUseCase {

    void makeDeposit(final String clientId, final String accountNumber, final BigDecimal amount, final TransactionType type);

}
