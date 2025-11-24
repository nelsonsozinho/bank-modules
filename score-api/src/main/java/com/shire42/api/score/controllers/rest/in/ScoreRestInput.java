package com.shire42.api.score.controllers.rest.in;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ScoreRestInput {

    private String cpf;
    private BigDecimal score;

}
