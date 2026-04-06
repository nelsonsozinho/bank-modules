package com.shire42.api.score.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Client {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private Score score;

}
