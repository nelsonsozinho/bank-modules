package com.shire42.api.availability.controllers.rest.in;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class RestrictionJsonInput {

    private String source;
    private BigDecimal value;
    private Boolean issActivated;
}
