package com.shire42.api.loan.controllers.rest.out;

public record ProductRestResponse(
        Long id,
        String description,
        Double minValue,
        Double maxValue
)
{
}
