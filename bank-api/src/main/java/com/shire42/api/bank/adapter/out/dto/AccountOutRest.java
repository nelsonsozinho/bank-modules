package com.shire42.api.bank.adapter.out.dto;

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
