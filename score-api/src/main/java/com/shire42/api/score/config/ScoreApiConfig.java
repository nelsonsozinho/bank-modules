package com.shire42.api.score.config;

import com.shire42.api.score.domain.port.out.FindClientRepository;
import com.shire42.api.score.domain.port.out.FindScoreRepository;
import com.shire42.api.score.domain.port.out.NewScoreRepository;
import com.shire42.api.score.domain.port.out.UpdateScoreRepository;
import com.shire42.api.score.domain.servuce.FindScoreService;
import com.shire42.api.score.domain.servuce.NewScoreService;
import com.shire42.api.score.domain.servuce.UpdateScoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScoreApiConfig {

    @Bean
    public FindScoreService configScoreService(final FindScoreRepository findScoreRepository) {
        return new FindScoreService(findScoreRepository);
    }

    @Bean
    public UpdateScoreService updateScoreService(
            final FindScoreRepository findScoreRepository,
            final UpdateScoreRepository updateScoreRepository) {
        return new UpdateScoreService(findScoreRepository, updateScoreRepository);
    }

    @Bean
    public NewScoreService newScoreService(
            final FindClientRepository findClientRepository,
            final NewScoreRepository newScoreRepository) {
        return new NewScoreService(findClientRepository, newScoreRepository);
    }

}
