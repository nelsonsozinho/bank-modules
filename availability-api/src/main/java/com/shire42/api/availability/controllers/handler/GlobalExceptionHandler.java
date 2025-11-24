package com.shire42.api.availability.controllers.handler;

import com.shire42.api.availability.service.exception.ClientNotFoundException;
import com.shire42.api.availability.service.exception.RestrictionNotFoundException;
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

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>>  handleClientFounds(ClientNotFoundException ex) throws Exception {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestrictionNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>>  handleRestrictionNotFounds(RestrictionNotFoundException ex) throws Exception {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
