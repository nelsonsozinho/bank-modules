package com.shire42.api.loan.service.exception;

public class SimulationWithSameValueException extends RuntimeException {

    public SimulationWithSameValueException(String msg) {
        super(msg);
    }

}
