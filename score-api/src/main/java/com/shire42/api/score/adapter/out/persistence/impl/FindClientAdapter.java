package com.shire42.api.score.adapter.out.persistence.impl;

import com.shire42.api.score.adapter.out.persistence.model.ClientEntity;
import com.shire42.api.score.adapter.out.persistence.repository.ClientRepository;
import com.shire42.api.score.domain.model.Client;
import com.shire42.api.score.domain.port.out.FindClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindClientAdapter implements FindClientRepository {

    private final ClientRepository clientRepository;

    @Override
    public Client findClientByCpf(final String cpf) {
        final ClientEntity clientEntity = clientRepository.findClientEntityByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Client not found with CPF: " + cpf));
        return Client.builder()
                .id(clientEntity.getId())
                .name(clientEntity.getName())
                .email(clientEntity.getEmail())
                .cpf(clientEntity.getCpf())
                .build();
    }

}
