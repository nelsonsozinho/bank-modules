package com.shire42.api.score.domain.port.out;

import com.shire42.api.score.domain.model.Score;

public interface FindScoreRepository {

    Score findScoreByClientCpf(final String cpf);

}
