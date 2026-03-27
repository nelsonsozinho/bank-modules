package com.shire42.api.bank.domain.port.in;

import java.math.BigDecimal;

public interface AccountWithdrawalUseCase {

    void makeWithdrawal(final String clientId, final String accountNumber, final BigDecimal amount);

}
