package com.shire42.api.score.domain.port.out;

import com.shire42.api.score.domain.model.Client;


public interface FindClientRepository {

    Client findClientByCpf(final String cpf);

}
