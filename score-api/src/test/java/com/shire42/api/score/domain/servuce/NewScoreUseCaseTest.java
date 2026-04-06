package com.shire42.api.score.domain.servuce;

import com.shire42.api.score.domain.model.Client;
import com.shire42.api.score.domain.model.Score;
import com.shire42.api.score.domain.port.out.FindClientRepository;
import com.shire42.api.score.domain.port.out.NewScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NewScoreUseCaseTest {

    @Mock
    private FindClientRepository findClientRepository;

    @Mock
    private NewScoreRepository newScoreRepository;

    private NewScoreService newScoreService;

    @BeforeEach
    void setup() {
        this.newScoreService = new NewScoreService(findClientRepository, newScoreRepository);
    }

    @Test
    void createNewScoreWithSuccess() {
        String cpf = "00000000000";
        BigDecimal newValue = new BigDecimal("812.35");
        Client client = Client.builder()
                .id(2L)
                .cpf(cpf)
                .name("John Doe")
                .email("john.doe@mail.com")
                .build();

        when(findClientRepository.findClientByCpf(cpf)).thenReturn(client);
        when(newScoreRepository.createNewScore(org.mockito.ArgumentMatchers.any(Score.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Score result = newScoreService.newScore(cpf, newValue);

        assertNotNull(result);
        assertNotNull(result.getLastUpdate());
        assertEquals(cpf, result.getClient().getCpf());
        assertEquals(newValue.doubleValue(), result.getScore());

        ArgumentCaptor<Score> scoreArgumentCaptor = ArgumentCaptor.forClass(Score.class);
        verify(newScoreRepository).createNewScore(scoreArgumentCaptor.capture());

        Score capturedScore = scoreArgumentCaptor.getValue();
        assertEquals(cpf, capturedScore.getClient().getCpf());
        assertEquals(newValue.doubleValue(), capturedScore.getScore());
        assertNotNull(capturedScore.getLastUpdate());
    }

    @Test
    void throwExceptionWhenClientIsNotFound() {
        String cpf = "99999999999";
        RuntimeException expectedException = new RuntimeException("Client not found with CPF: " + cpf);

        when(findClientRepository.findClientByCpf(cpf)).thenThrow(expectedException);

        RuntimeException result = assertThrows(RuntimeException.class,
                () -> newScoreService.newScore(cpf, BigDecimal.TEN));

        assertEquals(expectedException.getMessage(), result.getMessage());
    }

}

