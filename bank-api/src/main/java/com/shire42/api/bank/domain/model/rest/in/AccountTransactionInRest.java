package com.shire42.api.bank.domain.model.rest.in;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AccountTransactionInRest {

    private String sourceAccount;
    private String targetAccount;
    private BigDecimal amount;

}
