package com.shire42.api.availability.controllers.rest.out;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class RestrictionJsonOutput {

    private Long id;
    private String source;
    private BigDecimal value;
    private LocalDate lastUpdate;

}
