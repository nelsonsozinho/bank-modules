package com.shire42.api.bank.domain.model.rest.in;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class DepositTransactionRest {

    private String accountNumber;
    private String clientId;
    private BigDecimal amount;

}
