package com.shire42.api.score.domain.port.in;

import com.shire42.api.score.domain.model.Score;

import java.math.BigDecimal;

public interface NewScoreUseCase {

    Score newScore(final String cpf, final BigDecimal newValue);

}
