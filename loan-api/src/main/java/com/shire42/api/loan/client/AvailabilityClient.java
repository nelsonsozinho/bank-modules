package com.shire42.api.loan.client;

import com.shire42.api.loan.client.dto.ClientRestriction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "${api.availability.name}",
        url = "${api.availability.url}")
public interface AvailabilityClient {

    @GetMapping("/client/{cpf}")
    List<ClientRestriction> getRestrictionsByUser(@PathVariable("cpf") String cpf);

}
