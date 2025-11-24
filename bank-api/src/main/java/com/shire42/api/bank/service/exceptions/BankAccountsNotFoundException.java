package com.shire42.api.bank.service.exceptions;

public class BankAccountsNotFoundException extends RuntimeException {

    public BankAccountsNotFoundException(String message) {
        super(message);
    }

}
