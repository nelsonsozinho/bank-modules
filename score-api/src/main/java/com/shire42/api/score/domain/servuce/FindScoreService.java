package com.shire42.api.score.domain.servuce;

import com.shire42.api.score.domain.model.Score;
import com.shire42.api.score.domain.port.in.FindScoreUseCase;
import com.shire42.api.score.domain.port.out.FindScoreRepository;

public class FindScoreService implements FindScoreUseCase {

    private final FindScoreRepository findScoreRepository;

    public FindScoreService(final FindScoreRepository findScoreRepository) {
        this.findScoreRepository = findScoreRepository;
    }

    @Override
    public Score findScoreByClientCpf(String cpf) {
        return findScoreRepository.findScoreByClientCpf(cpf);
    }

}
