package com.shire42.api.bank.domain.exceptions;

public class InsuficientFoundsException extends RuntimeException {

    public InsuficientFoundsException(String message) {
        super(message);
    }

}
