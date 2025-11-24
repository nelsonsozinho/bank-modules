package com.shire42.api.availability.controllers;

import com.shire42.api.availability.service.ClientService;
import com.shire42.api.availability.service.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<?> createNewClientWithRestrictions(@RequestBody final ClientDto clientDto) {
        Long clientId = clientService.newClientWithRestrictions(clientDto);
        return ResponseEntity.created(URI.create(String.format("/client/%d", clientId))).build();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClientDto> getClient(@PathVariable("cpf") final String cpf) {
        return ResponseEntity.ok(clientService.getClientByCpf(cpf));
    }

}
