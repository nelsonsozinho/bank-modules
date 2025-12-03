package com.shire42.client.controller;

import com.shire42.client.domain.rest.in.ClientRest;
import com.shire42.client.domain.rest.out.ClientDto;
import com.shire42.client.domain.service.ClientService;
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

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clientService.findClientById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable("email") String email) {
        return ResponseEntity.ok(clientService.findClientByEmail(email));
    }

    @PostMapping
    public ResponseEntity<?> saveNewClient(@RequestBody ClientRest client) {
        ClientDto clientDto = clientService.saveNewClient(client);
        return ResponseEntity.created(URI.create(
                new StringBuilder()
                        .append("/client")
                        .append("/")
                        .append(clientDto.getClientId())
                        .toString())).body(clientDto);
    }

}
