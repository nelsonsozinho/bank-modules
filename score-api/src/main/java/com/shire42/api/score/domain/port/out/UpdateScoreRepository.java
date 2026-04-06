package com.shire42.api.score.domain.port.out;

import java.math.BigDecimal;

public interface UpdateScoreRepository {

    void updateScore(final String cpf, final BigDecimal newValue);

}
