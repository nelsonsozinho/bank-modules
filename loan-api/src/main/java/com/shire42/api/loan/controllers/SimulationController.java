package com.shire42.api.loan.controllers;

import com.shire42.api.loan.controllers.rest.in.SimulationInput;
import com.shire42.api.loan.service.SimulationService;
import com.shire42.api.loan.service.dto.SimulationDto;
import com.shire42.api.loan.service.dto.SimulationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/simulation", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class SimulationController {

    private final SimulationService simulationService;

    @PostMapping("/simulate")
    public ResponseEntity<SimulationResponse> createNewSimulation(
            @RequestBody final SimulationInput simulationInput) {
        var simulation = SimulationDto.builder()
                .instalments(simulationInput.instalments())
                .productId(simulationInput.productId())
                .value(simulationInput.value())
                .cpf(simulationInput.cpf())
                .bancAccountNumber(simulationInput.bankAccountNumber())
                .build();
        return ResponseEntity.ok(simulationService.newSimulation(simulation));
    }

    @GetMapping("/client/{cpf}/product/{productId}")
    public ResponseEntity<List<SimulationResponse>> listSimulations(
            @PathVariable("productId") Long productId,
            @PathVariable("cpf") String cpf) {
        return ResponseEntity.ok(simulationService.listSimulationsByProduct(productId, cpf));
    }

}
