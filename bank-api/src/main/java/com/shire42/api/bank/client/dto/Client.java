package com.shire42.api.bank.client.dto;

import lombok.Builder;

@Builder
public record Client(
    String clientId,
    String name,
    String email,
    String rg,
    String cpf,
    Address address
) {
}
