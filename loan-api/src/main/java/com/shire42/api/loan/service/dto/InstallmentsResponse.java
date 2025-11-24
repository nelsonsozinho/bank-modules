package com.shire42.api.loan.service.dto;

public record InstallmentsResponse(
        Integer number,
        Double discount,
        Double total,
        Double value,
        Double totalWithDiscount
) {

}
