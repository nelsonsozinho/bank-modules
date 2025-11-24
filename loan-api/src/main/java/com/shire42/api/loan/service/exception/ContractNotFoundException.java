package com.shire42.api.loan.service.exception;

public class ContractNotFoundException extends RuntimeException {

    public ContractNotFoundException(String message) {
        super(message);
    }

}
