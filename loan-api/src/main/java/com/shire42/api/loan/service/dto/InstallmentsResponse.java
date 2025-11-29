package com.shire42.api.loan.service.dto;

import java.io.Serializable;

public record InstallmentsResponse(
        Integer number,
        Double discount,
        Double total,
        Double value,
        Double totalWithDiscount
) implements Serializable {

}
