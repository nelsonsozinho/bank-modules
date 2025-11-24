package com.shire42.api.loan.service.exception;

public class ClientWithRestrictionsException extends RuntimeException {

    public ClientWithRestrictionsException(String message) {
        super(message);
    }

}
