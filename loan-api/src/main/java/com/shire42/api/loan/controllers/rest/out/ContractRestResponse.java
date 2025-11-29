package com.shire42.api.loan.controllers.rest.out;

import java.io.Serializable;
import java.util.List;

public record ContractRestResponse(
        Long id,
        String text,
        Boolean isAssign,
        Boolean completed,
        ProductRestResponse product,
        List<SimulationResponse> simulations
) implements Serializable
{

}
