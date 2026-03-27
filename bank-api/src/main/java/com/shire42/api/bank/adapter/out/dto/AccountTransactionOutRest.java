package com.shire42.api.bank.adapter.out.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountTransactionOutRest {
    private String status;
    private String message;
}