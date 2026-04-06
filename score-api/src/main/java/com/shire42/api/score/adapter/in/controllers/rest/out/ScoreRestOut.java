package com.shire42.api.score.adapter.in.controllers.rest.out;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class ScoreRestOut implements Serializable {

    private String cpf;
    private LocalDate lastUpdate;
    private BigDecimal score;

}
