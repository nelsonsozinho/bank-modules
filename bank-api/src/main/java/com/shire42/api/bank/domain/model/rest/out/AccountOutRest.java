package com.shire42.api.bank.domain.model.rest.out;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class AccountOutRest implements Serializable {

    private Long accountId;
    private String number;
    private BigDecimal balance;

}
