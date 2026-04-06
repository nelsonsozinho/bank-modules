package com.shire42.api.score.domain.servuce;

import com.shire42.api.score.domain.model.Score;
import com.shire42.api.score.domain.port.in.UpdateScoreUseCase;
import com.shire42.api.score.domain.port.out.FindScoreRepository;
import com.shire42.api.score.domain.port.out.UpdateScoreRepository;

import java.math.BigDecimal;

public class UpdateScoreService implements UpdateScoreUseCase {

    private final FindScoreRepository findScoreRepository;

    private final UpdateScoreRepository updateScoreRepository;

    public UpdateScoreService(
            final FindScoreRepository findScoreRepository,
            final UpdateScoreRepository updateScoreRepository) {
        this.findScoreRepository = findScoreRepository;
        this.updateScoreRepository = updateScoreRepository;
    }


    @Override
    public String updateScore(String cpf, BigDecimal newValue) {
        final Score score = findScoreRepository.findScoreByClientCpf(cpf);
        updateScoreRepository.updateScore(score.getClient().getCpf(), newValue);
        return cpf;
    }
}
