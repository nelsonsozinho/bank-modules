package com.shire42.api.bank.domain.port.in;

import java.math.BigDecimal;

public interface AccountTransferUseCase {

    void transfer(final String sourceAccountNumber, final String targetAccountNumber, final BigDecimal value);

}