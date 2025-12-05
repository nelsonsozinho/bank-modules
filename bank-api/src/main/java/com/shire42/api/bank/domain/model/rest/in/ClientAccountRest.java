package com.shire42.api.bank.domain.model.rest.in;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ClientAccountRest(
    String name,
    String cpf,
    String rg,
    String email,
    BigDecimal monthRenderedAmount,
    BigDecimal initialBalance,
    AddressClientAccountRest address
)
{
}
