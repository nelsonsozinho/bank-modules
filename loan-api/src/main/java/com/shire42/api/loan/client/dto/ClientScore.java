package com.shire42.api.loan.client.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class ClientScore {

    String cpf;
    LocalDate lastUpdate;
    BigDecimal score;

}
