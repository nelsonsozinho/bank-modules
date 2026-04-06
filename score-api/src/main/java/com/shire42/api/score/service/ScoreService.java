package com.shire42.api.score.service;

import com.shire42.api.score.adapter.in.controllers.rest.out.ScoreRestOut;

import java.math.BigDecimal;

public interface ScoreService {

    ScoreRestOut findScoreByClientCpf(final String cpf);

    String updateScore(final String cpf, final BigDecimal newValue);

}
