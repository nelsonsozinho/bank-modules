package com.shire42.api.loan.service;

import com.shire42.api.loan.model.Contract;
import com.shire42.api.loan.service.dto.ContractDto;
import com.shire42.api.loan.service.dto.SimulationDto;
import com.shire42.api.loan.service.dto.SimulationResponse;

import java.util.List;

public interface ContractService {

    List<Contract> listAllActiveContractsByUser(final String cpf);

    ContractDto dealContract(Long contractId, Long simulationId);

    ContractDto finishContract(final Long contractId);

}
