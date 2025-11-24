package com.shire42.api.bank.domain.model.rest.out;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountOutRest {

    private String number;
    private Double balance;

}
