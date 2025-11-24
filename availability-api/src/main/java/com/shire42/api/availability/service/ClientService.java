package com.shire42.api.availability.service;

import com.shire42.api.availability.service.dto.ClientDto;

public interface ClientService {

    ClientDto getClientByCpf(final String cpf);

    ClientDto newClient(final String cpf, final String name);

    Long newClientWithRestrictions(ClientDto clientDto);

}
