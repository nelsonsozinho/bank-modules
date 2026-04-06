package com.shire42.api.score.domain.port.in;

import com.shire42.api.score.domain.model.Client;

public interface FindClientUseCase {

    Client findClientByCpf(final String cpf);

}
