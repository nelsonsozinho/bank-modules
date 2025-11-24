package com.shire42.api.loan.service.exception;

public class ClientWithInsufficientScoreException extends RuntimeException {

    public ClientWithInsufficientScoreException(String message) {
        super(message);
    }

}
