package com.shire42.api.score.adapter.out.persistence.impl;

import com.shire42.api.score.adapter.out.persistence.model.ScoreEntity;
import com.shire42.api.score.adapter.out.persistence.repository.ScoreRepository;
import com.shire42.api.score.domain.port.out.UpdateScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateScoreAdapter implements UpdateScoreRepository {

    private final ScoreRepository scoreRepository;

    @Override
    public void updateScore(final String cpf, final BigDecimal newValue) {
        final Optional<ScoreEntity> score = scoreRepository.findByClientCpf(cpf);
            ScoreEntity s = score.orElseThrow(() -> new RuntimeException("Score not found for client with CPF: " + cpf));
            s.setScore(newValue.doubleValue());
            s.setLastUpdate(LocalDate.now());
            scoreRepository.save(s);
    }
}
