package com.shire42.api.bank.client.dto;

import lombok.Builder;

@Builder
public record Address(
        Long idAddress,
        String street,
        String zipCode,
        String neighborhood,
        String state,
        String country,
        Boolean isOfficialAddress
) {
}
