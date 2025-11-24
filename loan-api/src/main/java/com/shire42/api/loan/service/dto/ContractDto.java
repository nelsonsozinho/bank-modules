package com.shire42.api.loan.service.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ContractDto {

    private Long id;
    private Boolean isAssigned;
    private Boolean isCompleted;
    private String bankAccountNumber;
    private SimulationDto simulationDto;

}
