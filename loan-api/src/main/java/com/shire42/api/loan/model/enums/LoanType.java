package com.shire42.api.loan.model.enums;

public enum LoanType {

    IMOBILIARIO(1),
    CONSIGNADO(2),
    AUTOMOTIVO(3),
    INSS(4),
    PESSOAL(5);

    private Integer id;

    LoanType(final Integer type) {
        this.id = type;
    }

}
