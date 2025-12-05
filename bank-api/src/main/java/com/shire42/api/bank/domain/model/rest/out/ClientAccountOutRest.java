package com.shire42.api.bank.domain.model.rest.out;

import lombok.Builder;

@Builder
public record ClientAccountOutRest(
        String name,
        String email,
        String accountNumber
)
{
}
