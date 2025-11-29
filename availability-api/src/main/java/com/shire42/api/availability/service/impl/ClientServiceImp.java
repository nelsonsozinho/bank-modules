package com.shire42.api.availability.service.impl;

import com.shire42.api.availability.model.Client;
import com.shire42.api.availability.model.FinancialRestriction;
import com.shire42.api.availability.repository.ClientRepository;
import com.shire42.api.availability.repository.FinancialRestrictionRepository;
import com.shire42.api.availability.service.ClientService;
import com.shire42.api.availability.service.dto.ClientDto;
import com.shire42.api.availability.service.dto.RestrictionDto;
import com.shire42.api.availability.service.exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImp implements ClientService {

    private final ClientRepository repository;

    private final FinancialRestrictionRepository financialRestrictionRepository;


    @Override
    @Cacheable(value = "clientCache", key = "#cpf")
    public ClientDto getClientByCpf(final String cpf) {
        Client client = repository.findByCpf(cpf).orElseThrow(() -> new ClientNotFoundException(String.format("Client with cpf %s not found", cpf)));
        return ClientDto.builder()
                .name(client.getName())
                .cpf(client.getCpf())
                .id(client.getId())
                .restrictions(mapRestrictionsDtoFromClient(client))
                .build();
    }

    @Override
    @Transactional
    public ClientDto newClient(final String cpf, final String name) {
        Client newClient = saveClient(cpf, name);
        return ClientDto.builder()
                .cpf(newClient.getCpf())
                .name(newClient.getName())
                .build();
    }

    @Override
    @Transactional
    public Long newClientWithRestrictions(ClientDto clientDto) {
        Client newClient = saveClient(clientDto.getCpf(), clientDto.getName());
        List<FinancialRestriction> restrictions = clientDto.getRestrictions().stream().map(r -> FinancialRestriction.builder()
                .idActivated(true)
                .source(r.getSource())
                .lastUpdate(LocalDate.now())
                .value(r.getValue())
                .client(newClient)
                .build())
        .toList();
        return newClient.getId();
    }

    private Client saveClient(final String cpf, final String name) {
        Client client = new Client();
        client.setCpf(cpf);
        client.setName(name);
        return repository.save(client);
    }

    private List<FinancialRestriction> saveFinancialRestrictions(final List<FinancialRestriction> financialRestrictions) {
         return financialRestrictionRepository.saveAll(financialRestrictions);
    }

    private static List<RestrictionDto> mapRestrictionsDtoFromClient(final Client client) {
        return client.getRestrictions().stream().map(r -> RestrictionDto.builder()
                .value(r.getValue())
                .id(r.getId())
                .source(r.getSource())
                .lastUpdate(r.getLastUpdate())
                .isActivated(r.getIdActivated())
                .build()).toList();
    }

}
