package com.shire42.api.loan.service.impl;

import com.shire42.api.loan.client.AvailabilityClient;
import com.shire42.api.loan.client.ScoreClient;
import com.shire42.api.loan.client.dto.ClientScore;
import com.shire42.api.loan.model.Client;
import com.shire42.api.loan.model.Contract;
import com.shire42.api.loan.model.Installment;
import com.shire42.api.loan.model.Product;
import com.shire42.api.loan.model.Simulation;
import com.shire42.api.loan.repository.ClientRepository;
import com.shire42.api.loan.repository.ContractRepository;
import com.shire42.api.loan.repository.InstalmentRepository;
import com.shire42.api.loan.repository.ProductRepository;
import com.shire42.api.loan.repository.SimulationRepository;
import com.shire42.api.loan.service.SimulationService;
import com.shire42.api.loan.service.dto.InstallmentsResponse;
import com.shire42.api.loan.service.dto.SimulationDto;
import com.shire42.api.loan.service.dto.SimulationResponse;
import com.shire42.api.loan.service.exception.ClientWithInsufficientScoreException;
import com.shire42.api.loan.service.exception.ClientWithRestrictionsException;
import com.shire42.api.loan.service.exception.ProductNotFoundException;
import com.shire42.api.loan.service.exception.SimulationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SimulationServiceImp implements SimulationService {

    private static final Integer SIMULATION_INSTALMENT_LENGTH = 12;


    private final SimulationRepository simulationRepository;

    private final SimulationRepository repository;

    private final ClientRepository clientRepository;

    private final AvailabilityClient availabilityClient;

    private final ScoreClient scoreClient;

    private final ProductRepository productRepository;

    private final InstalmentRepository installmentRepository;

    private final ContractRepository contractRepository;


    @Override
    @Cacheable(value = "simulationListCacheCache", key = "{#productId, #cpf}")
    public List<SimulationResponse> listSimulationsByProduct(Long productId, String cpf) {
        return repository.listSimulationsFromProductAndClient(productId, cpf)
                .stream()
                .map(this::toSimulationResponse)
                .toList();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SimulationResponse newSimulation(final SimulationDto simulationDto) {
        validateRestrictions(simulationDto);
        hasSimulationWithSameValue(simulationDto.getProductId(), simulationDto.getInstalments(), simulationDto.getValue());

        final Client client = clientRepository.findClientByCpf(simulationDto.getCpf()).orElseThrow(() ->
                new ProductNotFoundException(String.format("CLeint with cpf %s not found", simulationDto.getCpf())));
        final Product product = this.getProductFromClient(client, simulationDto.getProductId());

        Simulation simulation = new Simulation();
        simulation.setTotal(simulationDto.getValue());
        simulation.setInstallmentSize(simulationDto.getInstalments());
        simulation.setProduct(product);
        simulation.setIsEffective(false);
        simulation.setContract(createNewContract(client, simulationDto.getBancAccountNumber(), product));
        Simulation newSimulation = simulationRepository.save(simulation);

        List<Installment> installments = createInstalments(simulation,simulation.getInstallmentSize());
        newSimulation.setInstallments(installments);
        return simulationToResponse(simulationRepository.save(newSimulation));
    }

    private void hasSimulationWithSameValue(Long productId, Integer installmentSize, Double value) {
        List<Simulation> simulations = simulationRepository.listWithSameValueAndNotEffective(installmentSize, value, productId);
        if(!simulations.isEmpty()) {
            throw new SimulationNotFoundException("There is already simulation with the same value!");
        }
    }

    private Contract createNewContract(Client client, String bancAccountNumber, Product product) {
        Contract contract = new Contract();
        contract.setText("");
        contract.setIsAssign(false);
        contract.setCompleted(false);
        contract.setClient(client);
        contract.setBancAccountNumber(bancAccountNumber);
        contract.setProduct(product);
        return contractRepository.save(contract);
    }

    private SimulationResponse toSimulationResponse(final Simulation simulation) {
        return new SimulationResponse(
                simulation.getId(),
                simulation.getContract().getId(),
                simulation.getTotal(),
                simulation.getProduct().getDescription(),
                simulation.getInstallmentSize(),
                fillInstallments(simulation.getInstallments()),
                simulation.getIsEffective()
        );
    }

    private List<InstallmentsResponse> fillInstallments(List<Installment> installments) {
        return installments.stream()
                .map(i -> new InstallmentsResponse(
                        i.getNumber(),
                        i.getDiscount(),
                        i.getTotal(),
                        i.getValue(),
                        i.getTotalWithDiscount()
                ))
                .toList();
    }

    private List<InstallmentsResponse> installmentsToResponse(final List<Installment> installments) {
        return installments.stream().map(inst -> new InstallmentsResponse(
                inst.getNumber(),
                inst.getDiscount(),
                inst.getTotal(),
                inst.getValue(),
                inst.getTotalWithDiscount()
        )).toList();
    }

    public void validateRestrictions(SimulationDto dto) {
        if(!availabilityClient.getRestrictionsByUser(dto.getCpf()).isEmpty()) {
            throw new ClientWithRestrictionsException(String.format("Client with cpf %s has restrictions", dto.getCpf()));
        }

        final ClientScore score = scoreClient.getScoreUser(dto.getCpf());
        final BigDecimal result = score.getScore().subtract(BigDecimal.valueOf(dto.getValue()));

        if(result.doubleValue() < 0) {
            throw new ClientWithInsufficientScoreException(String.format("Client with cpf %s has no enough score", dto.getCpf()));
        }
    }

    private Product getProductFromClient(final Client client, final Long productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException(String.format("Product with id %s not found", productId)));
    }

    private List<Installment> createInstalments(final Simulation simulation, Integer instalmentSie) {
        final List<Installment> installments = new ArrayList<>();
        final var value = simulation.getTotal() / instalmentSie;
        for (int i = 0; i < instalmentSie; i++) {
            Installment installment = new Installment();
            installment.setDiscount(0.0);
            installment.setNumber(i + 1);
            installment.setValue(value);
            installment.setTotal(simulation.getTotal());
            installment.setTotalWithDiscount(0.0);
            installments.add(installment);
            installment.setSimulation(simulation);
        }
        return installmentRepository.saveAll(installments);
    }

    private SimulationResponse simulationToResponse(Simulation simulation) {
        List<InstallmentsResponse> instalments = installmentsToResponse(simulation.getInstallments());
        return new SimulationResponse(
                simulation.getId(),
                simulation.getContract().getId(),
                simulation.getTotal(),
                simulation.getProduct().getDescription(),
                instalments.size(),
                instalments,
                simulation.getIsEffective()
        );
    }

}
