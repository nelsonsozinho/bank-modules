package com.shire42.api.loan.controllers;

import com.shire42.api.loan.controllers.rest.out.ContractRestResponse;
import com.shire42.api.loan.controllers.rest.out.ProductRestResponse;
import com.shire42.api.loan.controllers.rest.out.SimulationResponse;
import com.shire42.api.loan.service.ContractService;
import com.shire42.api.loan.service.dto.ContractDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping("/{cpf}")
    public ResponseEntity<?> listContractsByUser(@PathVariable("cpf") String cpf) {
        var responseList = new ArrayList<>();
        contractService.listAllActiveContractsByUser(cpf)
                .forEach(x -> {
                    var product = x.getProduct();
                    var response = new ContractRestResponse(
                            x.getId(),
                            x.getText(),
                            x.getIsAssign(),
                            x.getCompleted(),
                            new ProductRestResponse(
                                    product.getId(),
                                    product.getDescription(),
                                    product.getMinValue(),
                                    product.getMaxValue()
                            ),
                            x.getSimulations().stream()
                                    .map(s -> {
                                        return new SimulationResponse(
                                                s.getId(),
                                                s.getInstallmentSize(),
                                                s.getIsEffective(),
                                                s.getTotal());
                                    }).toList()
                    );
                    responseList.add(response);
                });

        return ResponseEntity.ok(responseList);
    }

    @PatchMapping("/{contractId}/simulation/{simulationId}/deal")
    public ResponseEntity<ContractDto> dealContract(
            @PathVariable("contractId") Long contractId,
            @PathVariable("simulationId") Long simulationId) {
        return ResponseEntity.ok(contractService.dealContract(contractId, simulationId));
    }

    @PatchMapping("/{contractId}/finish")
    public ResponseEntity<ContractDto> finishContract(@PathVariable("contractId") Long contractId) {
        return ResponseEntity.ok(contractService.finishContract(contractId));
    }

}
