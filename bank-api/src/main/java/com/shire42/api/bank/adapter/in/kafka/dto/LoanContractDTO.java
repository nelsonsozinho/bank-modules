package com.shire42.api.bank.adapter.in.kafka.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoanContractDTO implements Serializable {
    Long contractId;
    String text;
    String productDescription;
    Long productId;
    Double loanValue;
    Integer instalmentSize;
    String bancAccountNumber;
    Long clientId;
}

