package com.shire42.api.loan.controllers.rest.out;

import java.io.Serializable;

public record SimulationResponse(
        Long id,
        Integer installmentSize,
        Boolean isEffective,
        Double total
) implements Serializable
{
}
