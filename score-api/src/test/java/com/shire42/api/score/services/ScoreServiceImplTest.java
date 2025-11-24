package com.shire42.api.score.services;

import com.shire42.api.score.model.Client;
import com.shire42.api.score.model.Score;
import com.shire42.api.score.repository.ClientRepository;
import com.shire42.api.score.repository.ScoreRepository;
import com.shire42.api.score.service.impl.ScoreServiceImpl;
import com.shire42.api.score.service.exception.ClientNotFoundException;
import com.shire42.api.score.service.exception.ClientScoreNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScoreServiceImplTest {

    @Mock
    private ScoreRepository repository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ScoreServiceImpl service;

    @Captor
    private ArgumentCaptor<Score> scoreCaptor;

    @Test
    void updateScore_updatesExistingScore() {
        String cpf = "11122233344";
        BigDecimal newVal = BigDecimal.valueOf(75);
        Score existing = new Score();
        existing.setScore(10.0);
        existing.setLastUpdate(LocalDate.of(2000, 1, 1));

        when(repository.findByClientCpf(cpf)).thenReturn(Optional.of(existing));
        when(repository.save(any(Score.class))).thenAnswer(inv -> inv.getArgument(0));

        String result = service.updateScore(cpf, newVal);

        assertEquals(cpf, result);
        verify(repository, times(1)).save(scoreCaptor.capture());
        Score saved = scoreCaptor.getValue();
        assertEquals(newVal.doubleValue(), saved.getScore());
        assertEquals(LocalDate.now(), saved.getLastUpdate());
    }

    @Test
    void updateScore_createsNewScoreWhenNotPresent() {
        String cpf = "55566677788";
        BigDecimal newVal = BigDecimal.valueOf(42);
        Client client = new Client();

        when(repository.findByClientCpf(cpf)).thenReturn(Optional.empty());
        when(clientRepository.findClientByCpf(cpf)).thenReturn(Optional.of(client));
        when(repository.save(any(Score.class))).thenAnswer(inv -> inv.getArgument(0));

        String result = service.updateScore(cpf, newVal);

        assertEquals(cpf, result);
        verify(repository, times(1)).save(scoreCaptor.capture());
        Score saved = scoreCaptor.getValue();
        assertEquals(newVal.doubleValue(), saved.getScore());
        assertSame(client, saved.getClient());
    }

    @Test
    void updateScore_throwsWhenClientNotFound() {
        String cpf = "00011122233";
        BigDecimal newVal = BigDecimal.valueOf(10);

        when(repository.findByClientCpf(cpf)).thenReturn(Optional.empty());
        when(clientRepository.findClientByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> service.updateScore(cpf, newVal));
        verify(repository, never()).save(any());
    }

    @Test
    void findScoreByClientCpf_returnsScoreWhenFound() {
        String cpf = "99988877766";
        Score s = new Score();
        when(repository.findByClientCpf(cpf)).thenReturn(Optional.of(s));

        Score result = service.findScoreByClientCpf(cpf);

        assertSame(s, result);
    }

    @Test
    void findScoreByClientCpf_throwsWhenNotFound() {
        String cpf = "44433322211";
        when(repository.findByClientCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(ClientScoreNotFoundException.class, () -> service.findScoreByClientCpf(cpf));
    }
}
