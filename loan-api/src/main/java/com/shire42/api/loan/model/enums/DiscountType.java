package com.shire42.api.loan.model.enums;

public enum DiscountType {

    ANTECIPATION(1),
    NEGOCIATION(2),
    NONE(3);

    private Integer type;

    DiscountType(final Integer type) {
        this.type = type;
    }

}
