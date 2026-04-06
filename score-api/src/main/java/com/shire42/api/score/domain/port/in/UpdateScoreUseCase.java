package com.shire42.api.score.domain.port.in;

import java.math.BigDecimal;

public interface UpdateScoreUseCase {

    String updateScore(final String cpf, final BigDecimal newValue);

}
