package com.shire42.api.score.adapter.out.persistence.impl;

import com.shire42.api.score.adapter.out.persistence.model.ScoreEntity;
import com.shire42.api.score.adapter.out.persistence.repository.ScoreRepository;
import com.shire42.api.score.domain.model.Client;
import com.shire42.api.score.domain.model.Score;
import com.shire42.api.score.domain.port.out.FindScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindScoreAdapter implements FindScoreRepository {

    private final ScoreRepository scoreRepository;

    @Override
    public Score findScoreByClientCpf(String cpf) {
        final ScoreEntity scoreEntity = scoreRepository.findByClientCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Score not found for client with CPF: " + cpf));
        return Score.builder()
                .score(scoreEntity.getScore())
                .lastUpdate(scoreEntity.getLastUpdate())
                .client(Client.builder()
                        .cpf(scoreEntity.getClient().getCpf())
                        .id(scoreEntity.getClient().getId())
                        .email(scoreEntity.getClient().getEmail())
                        .name(scoreEntity.getClient().getName())
                        .cpf(scoreEntity.getClient().getCpf())
                        .build())
                .build();

    }
}
