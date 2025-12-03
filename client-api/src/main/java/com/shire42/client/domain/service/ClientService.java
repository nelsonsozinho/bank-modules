package com.shire42.client.domain.service;

import com.shire42.client.domain.exception.ClientNotFountException;
import com.shire42.client.domain.model.Client;
import com.shire42.client.domain.repository.ClientRepository;
import com.shire42.client.domain.rest.in.ClientRest;
import com.shire42.client.domain.rest.out.AddressDto;
import com.shire42.client.domain.rest.out.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientDto findClientById(Long id) {
        final Client client = clientRepository.findById(id).orElseThrow(
                () -> new ClientNotFountException(String.format("Client with id %d not found.", id)));
        return clientWithAddressToClientDto(client);
    }

    public ClientDto findClientByEmail(String email) {
        final Client client = clientRepository.findByEmail(email).orElseThrow(
                () -> new ClientNotFountException(String.format("Client with email %s not found.", email)));
        return clientWithAddressToClientDto(client);
    }

    @Transactional
    public ClientDto saveNewClient(ClientRest clientRest) {
        Client newClient = clientRepository.save(Client.builder()
                .cpf(clientRest.cpf())
                .email(clientRest.email())
                .name(clientRest.name())
                .rg(clientRest.rg())
                .build());
        return clientToClientDto(newClient);

    }

    private static ClientDto clientWithAddressToClientDto(Client client) {
        return ClientDto.builder()
                .clientId(client.getId())
                .cpf(client.getCpf())
                .name(client.getName())
                .email(client.getEmail())
                .rg(client.getRg())
                .addresses(client.getAddresses().stream().map(
                                a -> AddressDto.builder()
                                        .idAddress(a.getId())
                                        .country(a.getCountry())
                                        .isOfficialAddress(a.getIsOfficialAddress())
                                        .neighborhood(a.getNeighborhood())
                                        .state(a.getState())
                                        .street(a.getStreet())
                                        .zipCode(a.getZipCode())
                                        .isOfficialAddress(a.getIsOfficialAddress())
                                        .build())
                        .toList())
                .build();
    }

    private static ClientDto clientToClientDto(Client client) {
        return ClientDto.builder()
                .clientId(client.getId())
                .cpf(client.getCpf())
                .email(client.getEmail())
                .rg(client.getRg())
                .name(client.getName())
                .build();
    }

}
