package com.shire42.api.bank.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoanContractEvent {

    Long contractId;
    String text;
    String productDescription;
    Long productId;
    Double loanValue;
    Integer instalmentSize;
    String bancAccountNumber;
    Long clientId;

}
