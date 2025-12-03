package com.shire42.client.domain.rest.in;

import lombok.Builder;

@Builder
public record ClientRest(
        String name,
        String cpf,
        String rg,
        String email
)
{
}
