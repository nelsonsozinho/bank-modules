package com.shire42.api.loan.controllers.handler;

import com.shire42.api.loan.service.exception.ClientNotFoundException;
import com.shire42.api.loan.service.exception.ClientWithInsufficientScoreException;
import com.shire42.api.loan.service.exception.ClientWithRestrictionsException;
import com.shire42.api.loan.service.exception.ContractAlreadyAssignException;
import com.shire42.api.loan.service.exception.ContractAlreadyCompletedException;
import com.shire42.api.loan.service.exception.ContractNotFoundException;
import com.shire42.api.loan.service.exception.ProductNotFoundException;
import com.shire42.api.loan.service.exception.SimulationNotFoundException;
import com.shire42.api.loan.service.exception.SimulationWithSameValueException;
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

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleClientNotFound(ClientNotFoundException ex) {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContractAlreadyAssignException.class)
    public ResponseEntity<Map<String, List<String>>> handleContractAlreadyAssigned(ContractAlreadyAssignException ex) {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContractNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleContractNotFound(ContractNotFoundException ex) {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleProductNotFound(ProductNotFoundException ex) {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SimulationNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleSimulationNotFound(SimulationNotFoundException ex) {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContractAlreadyCompletedException.class)
    public ResponseEntity<Map<String, List<String>>> handleContractAlreadyCompleted(ContractAlreadyCompletedException ex) {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientWithRestrictionsException.class)
    public ResponseEntity<Map<String, List<String>>> handleClientHasCreditRestrictions(ClientWithRestrictionsException ex) {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientWithInsufficientScoreException.class)
    public ResponseEntity<Map<String, List<String>>> handleClientWitInsufficientFounds(ClientWithInsufficientScoreException ex) {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SimulationWithSameValueException.class)
    public ResponseEntity<Map<String, List<String>>> handleSimulationWithSameValue(SimulationWithSameValueException ex) {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
