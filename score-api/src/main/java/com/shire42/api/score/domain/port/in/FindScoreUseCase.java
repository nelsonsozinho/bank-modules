package com.shire42.api.score.domain.port.in;

import com.shire42.api.score.domain.model.Score;

public interface FindScoreUseCase {

    Score findScoreByClientCpf(final String cpf);

}
