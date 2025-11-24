package com.shire42.api.loan.service;

import com.shire42.api.loan.model.Simulation;
import com.shire42.api.loan.service.dto.SimulationDto;
import com.shire42.api.loan.service.dto.SimulationResponse;

import java.util.List;

public interface SimulationService {

    List<SimulationResponse> listSimulationsByProduct(Long productId, String cpf);

    SimulationResponse newSimulation(final SimulationDto simulationDto);

}
