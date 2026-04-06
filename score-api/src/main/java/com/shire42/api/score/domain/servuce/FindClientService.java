package com.shire42.api.score.domain.servuce;

import com.shire42.api.score.domain.model.Client;
import com.shire42.api.score.domain.port.in.FindClientUseCase;
import com.shire42.api.score.domain.port.out.FindClientRepository;

public class FindClientService implements FindClientUseCase {

    private final FindClientRepository findClientRepository;

    public FindClientService(final FindClientRepository findClientRepository) {
        this.findClientRepository = findClientRepository;
    }

    @Override
    public Client findClientByCpf(final String cpf) {
        return findClientRepository.findClientByCpf(cpf);
    }

}

