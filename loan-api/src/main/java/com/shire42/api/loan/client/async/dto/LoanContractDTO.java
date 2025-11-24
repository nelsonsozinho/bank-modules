package com.shire42.api.loan.client.async.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
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