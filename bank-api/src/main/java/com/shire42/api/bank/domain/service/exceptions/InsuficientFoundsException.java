package com.shire42.api.bank.domain.service.exceptions;

public class InsuficientFoundsException extends RuntimeException {

    public InsuficientFoundsException(String message) {
        super(message);
    }

}
