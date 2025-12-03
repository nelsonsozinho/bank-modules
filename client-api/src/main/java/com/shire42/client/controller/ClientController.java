package com.shire42.client.controller;

import com.shire42.client.domain.rest.out.ClientDto;
import com.shire42.client.domain.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
