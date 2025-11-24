package com.shire42.api.loan.service.exception;

public class ContractAlreadyCompletedException extends RuntimeException {

    public ContractAlreadyCompletedException(String message) {
        super(message);
    }

}
