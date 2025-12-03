package com.shire42.client.controller;

import com.shire42.client.domain.rest.in.AddressRest;
import com.shire42.client.domain.rest.out.AddressDto;
import com.shire42.client.domain.service.AddressService;
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
@RequestMapping("/address")
@RequiredArgsConstructor
public class ClientAddressController {

    private final AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<AddressDto> getOfficialAddressByClient(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(addressService.getOfficialClientByAddress(clientId));
    }

    @PostMapping("/client/{clientId}")
    public ResponseEntity<AddressDto> saveNewAddressFromClient(
            @RequestBody AddressRest addressRest,
            @PathVariable("clientId") Long clientId) {
        AddressDto dto = addressService.saveNewAddress(addressRest, clientId);
        return ResponseEntity.created(URI.create(
                new StringBuilder()
                        .append("/address/")
                        .append(dto.getIdAddress())
                        .toString()))
                .body(dto);
    }

}
