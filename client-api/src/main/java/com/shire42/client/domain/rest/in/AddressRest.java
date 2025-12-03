package com.shire42.client.domain.rest.in;

import lombok.Builder;

@Builder
public record AddressRest(
        Long idAddress,
        String street,
        String zipCode,
        String neighborhood,
        String state,
        String country,
        Boolean isOfficialAddress
)
{
}
