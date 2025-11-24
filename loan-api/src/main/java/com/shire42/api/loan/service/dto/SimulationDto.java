package com.shire42.api.loan.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimulationDto {

    private Long id;
    private Double value;
    private Integer instalments;
    private Long productId;
    private String cpf;
    private String bancAccountNumber;

}
