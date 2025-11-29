package com.shire42.api.loan.controllers.rest.out;

import java.io.Serializable;

public record ProductRestResponse(
        Long id,
        String description,
        Double minValue,
        Double maxValue
) implements Serializable
{
}
