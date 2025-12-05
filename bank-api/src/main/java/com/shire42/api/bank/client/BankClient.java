package com.shire42.api.bank.client;

import com.shire42.api.bank.client.configuration.FeignSslConfig;
import com.shire42.api.bank.client.dto.Address;
import com.shire42.api.bank.client.dto.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "${api.bank.client.name}",
        url = "${api.bank.client.url}",
        configuration = FeignSslConfig.class)
public interface BankClient {

    @GetMapping("/client/{id}")
    Client getClientById(@PathVariable("id") Long id);

    @PostMapping("/client")
    Client saveNewClient(Client client);

    @PostMapping("/client/{clientId}/address")
    Address saveAddress(@RequestBody Address address, @PathVariable("clientId") Long clientId);

}
