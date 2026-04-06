package com.shire42.api.score.domain.servuce;

import com.shire42.api.score.domain.model.Client;
import com.shire42.api.score.domain.model.Score;
import com.shire42.api.score.domain.port.out.FindScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindScoreUseCaseTest {

    @Mock
    private FindScoreRepository findScoreRepository;

    private FindScoreService findScoreService;

    @BeforeEach
    void setup() {
        this.findScoreService = new FindScoreService(findScoreRepository);
    }

    @Test
    void searchScoreByCpfWithSuccess() {
        String cfp = "00000000000";

        when(findScoreRepository.findScoreByClientCpf(cfp)).thenReturn(
                Score.builder()
                        .id(1L)
                        .lastUpdate(LocalDate.now())
                        .score(800D)
                        .client(Client.builder()
                                .id(1L)
                                .cpf(cfp)
                                .name("John Doe")
                                .build())
                .build());

        Score scoreResult = findScoreService.findScoreByClientCpf(cfp);

        assertNotNull(scoreResult);
        assertTrue(scoreResult.getScore() > 0);
        assertEquals(800D, scoreResult.getScore());
    }

}
