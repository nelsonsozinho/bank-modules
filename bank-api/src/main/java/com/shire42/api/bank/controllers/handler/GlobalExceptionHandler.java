package com.shire42.api.bank.controllers.handler;

import com.shire42.api.bank.service.exceptions.BankAccountsNotFoundException;
import com.shire42.api.bank.service.exceptions.InsuficientFoundsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsuficientFoundsException.class)
    public ResponseEntity<Map<String, List<String>>>  handleInsufficientFounds(InsuficientFoundsException ex) throws Exception {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BankAccountsNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleAccountFounds(BankAccountsNotFoundException ex) throws Exception {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
