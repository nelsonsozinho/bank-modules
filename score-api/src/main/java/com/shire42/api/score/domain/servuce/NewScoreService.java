package com.shire42.api.score.domain.servuce;

import com.shire42.api.score.domain.model.Client;
import com.shire42.api.score.domain.model.Score;
import com.shire42.api.score.domain.port.in.NewScoreUseCase;
import com.shire42.api.score.domain.port.out.FindClientRepository;
import com.shire42.api.score.domain.port.out.NewScoreRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NewScoreService implements NewScoreUseCase {

    private final FindClientRepository findClientRepository;

    private final NewScoreRepository newScoreRepository;

    public NewScoreService(
            final FindClientRepository findClientRepository,
            final NewScoreRepository newScoreRepository) {
        this.newScoreRepository = newScoreRepository;
        this.findClientRepository = findClientRepository;
    }

    @Override
    public Score newScore(String cpf, BigDecimal newValue) {
        Client client = this.findClientRepository.findClientByCpf(cpf);
        Score score = Score.builder()
                .score(newValue.doubleValue())
                .lastUpdate(LocalDate.now())
                .client(client)
                .build();
        return newScoreRepository.createNewScore(score);
    }
}
