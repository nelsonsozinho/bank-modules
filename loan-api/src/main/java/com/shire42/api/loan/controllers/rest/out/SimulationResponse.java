package com.shire42.api.loan.controllers.rest.out;

public record SimulationResponse(
        Long id,
        Integer installmentSize,
        Boolean isEffective,
        Double total
)
{
}
