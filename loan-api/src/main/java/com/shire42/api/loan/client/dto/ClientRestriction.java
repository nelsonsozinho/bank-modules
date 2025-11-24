package com.shire42.api.loan.client.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class ClientRestriction {
    Long id;
    String source;
    BigDecimal value;
    LocalDate lastUpdate;
}


