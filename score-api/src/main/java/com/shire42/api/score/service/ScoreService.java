package com.shire42.api.score.service;

import com.shire42.api.score.model.Score;

import java.math.BigDecimal;

public interface ScoreService {

    Score findScoreByClientCpf(final String cpf);

    String updateScore(final String cpf, final BigDecimal newValue);

}
