package com.shire42.api.loan.controllers.rest.in;

public record SimulationInput(
        Double value,
        Long productId,
        Integer instalments,
        String cpf,
        String bankAccountNumber
) {
}
