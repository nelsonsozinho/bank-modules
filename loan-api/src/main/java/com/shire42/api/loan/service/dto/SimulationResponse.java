package com.shire42.api.loan.service.dto;

import java.io.Serializable;
import java.util.List;

public record SimulationResponse(
        Long simulationId,
        Long contractId,
        Double total,
        String productName,
        Integer installmentSize,
        List<InstallmentsResponse> installments,
        Boolean isEffective
) implements Serializable {

}
