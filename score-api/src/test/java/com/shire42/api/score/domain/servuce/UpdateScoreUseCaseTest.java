package com.shire42.api.score.domain.servuce;

import com.shire42.api.score.domain.model.Client;
import com.shire42.api.score.domain.model.Score;
import com.shire42.api.score.domain.port.out.FindScoreRepository;
import com.shire42.api.score.domain.port.out.UpdateScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateScoreUseCaseTest {

    @Mock
    private FindScoreRepository findScoreRepository;

    @Mock
    private UpdateScoreRepository updateScoreRepository;

    private UpdateScoreService updateScoreService;

    @BeforeEach
    void setup() {
        this.updateScoreService = new UpdateScoreService(findScoreRepository, updateScoreRepository);
    }

    @Test
    void updateScoreWithSuccess() {
        String cpf = "00000000000";
        BigDecimal newValue = new BigDecimal("930.00");
        Score score = Score.builder()
                .id(1L)
                .score(800D)
                .client(Client.builder().id(1L).cpf(cpf).name("John Doe").build())
                .build();

        when(findScoreRepository.findScoreByClientCpf(cpf)).thenReturn(score);

        String resultCpf = updateScoreService.updateScore(cpf, newValue);

        assertEquals(cpf, resultCpf);
        verify(findScoreRepository).findScoreByClientCpf(cpf);
        verify(updateScoreRepository).updateScore(cpf, newValue);
    }

    @Test
    void throwExceptionWhenScoreIsNotFound() {
        String cpf = "11111111111";
        RuntimeException expectedException = new RuntimeException("Score not found");

        when(findScoreRepository.findScoreByClientCpf(cpf)).thenThrow(expectedException);

        RuntimeException result = assertThrows(RuntimeException.class,
                () -> updateScoreService.updateScore(cpf, BigDecimal.ONE));

        assertEquals(expectedException.getMessage(), result.getMessage());
        verify(updateScoreRepository, never()).updateScore(cpf, BigDecimal.ONE);
    }

    @Test
    void throwExceptionWhenUpdateRepositoryFails() {
        String cpf = "22222222222";
        BigDecimal newValue = new BigDecimal("905.50");
        Score score = Score.builder()
                .id(2L)
                .score(700D)
                .client(Client.builder().id(2L).cpf(cpf).name("Jane Doe").build())
                .build();

        when(findScoreRepository.findScoreByClientCpf(cpf)).thenReturn(score);
        RuntimeException expectedException = new RuntimeException("Database error");
        org.mockito.Mockito.doThrow(expectedException).when(updateScoreRepository).updateScore(cpf, newValue);

        RuntimeException result = assertThrows(RuntimeException.class,
                () -> updateScoreService.updateScore(cpf, newValue));

        assertEquals(expectedException.getMessage(), result.getMessage());
    }

}

