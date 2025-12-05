package com.shire42.api.bank.domain.model.rest.in;

import lombok.Builder;

@Builder
public record AddressClientAccountRest(
        String street,
        String zipCode,
        String neighborhood,
        String number,
        String city,
        String state,
        String country,
        Boolean isOfficialAddress
) {
}
