package com.shire42.api.score.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Score {

    private Long id;
    private Client client;
    private LocalDate lastUpdate;
    private Double score;

}
