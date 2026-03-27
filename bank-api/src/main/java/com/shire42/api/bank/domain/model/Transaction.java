package com.shire42.api.bank.domain.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Builder
@Data
public class Transaction {

    private Long id;
    private Long clientId;
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private Double amount;
    private String transactionType;
    private LocalDate date;

}
