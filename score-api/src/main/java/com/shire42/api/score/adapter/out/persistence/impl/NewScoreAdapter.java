package com.shire42.api.score.adapter.out.persistence.impl;

import com.shire42.api.score.adapter.out.persistence.model.ClientEntity;
import com.shire42.api.score.adapter.out.persistence.model.ScoreEntity;
import com.shire42.api.score.adapter.out.persistence.repository.ClientRepository;
import com.shire42.api.score.adapter.out.persistence.repository.ScoreRepository;
import com.shire42.api.score.domain.model.Score;
import com.shire42.api.score.domain.port.out.NewScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class NewScoreAdapter implements NewScoreRepository {

    private final ScoreRepository scoreRepository;

    private final ClientRepository clientRepository;


    @Override
    public Score createNewScore(final Score score) {
        final String cpf = score.getClient().getCpf();
        ClientEntity clientEntity = clientRepository.findClientEntityByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Client not found for CPF: " + cpf));
        ScoreEntity entity = new ScoreEntity();
        entity.setScore(score.getScore());
        entity.setLastUpdate(LocalDate.now());
        entity.setClient(clientEntity);
        ScoreEntity newEntity = scoreRepository.save(entity);
        return Score.builder()
                .score(newEntity.getScore())
                .lastUpdate(newEntity.getLastUpdate())
                .client(score.getClient())
                .build();
    }

}
