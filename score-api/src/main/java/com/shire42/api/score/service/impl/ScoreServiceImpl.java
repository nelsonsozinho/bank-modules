package com.shire42.api.score.service.impl;

import com.shire42.api.score.controllers.rest.out.ScoreRestOut;
import com.shire42.api.score.model.Client;
import com.shire42.api.score.model.Score;
import com.shire42.api.score.repository.ClientRepository;
import com.shire42.api.score.repository.ScoreRepository;
import com.shire42.api.score.service.ScoreService;
import com.shire42.api.score.service.exception.ClientNotFoundException;
import com.shire42.api.score.service.exception.ClientScoreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository repository;

    private final ClientRepository clientRepository;

    @Override
    @Cacheable(value = "scoreClientCache", key = "#cpf")
    public ScoreRestOut findScoreByClientCpf(final String cpf) {
        Score score = repository.findByClientCpf(cpf)
                .orElseThrow(() -> new ClientScoreNotFoundException(String.format("Score from client %s not found", cpf)));
        return ScoreRestOut.builder()
                .cpf(cpf)
                .lastUpdate(score.getLastUpdate())
                .score(new BigDecimal(score.getScore()))
                .build();
    }

    @Override
    @Transactional
    public String updateScore(final String cpf, final BigDecimal newValue) {
        final Optional<Score> score = repository.findByClientCpf(cpf);

        if(score.isPresent()) {
            Score s = score.get();
            s.setScore(newValue.doubleValue());
            s.setLastUpdate(LocalDate.now());
            repository.save(s);
        } else {
            Score newScore = new Score();
            Client client = clientRepository.findClientByCpf(cpf).orElseThrow(() ->
                    new ClientNotFoundException(String.format("Client with cpf %s not found", cpf)));
            newScore.setScore(newValue.doubleValue());
            newScore.setClient(client);
            repository.save(newScore);
        }

        return cpf;
    }

}
