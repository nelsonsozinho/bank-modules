package com.shire42.api.loan.repository;

import com.shire42.api.loan.model.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {

    @Query("from Simulation as simulation " +
            "inner join simulation.product product " +
            "inner join product.contracts contract " +
            "inner join contract.client client " +
            "where client.cpf = :cpf " +
            "and contract.isAssign = false " +
            "and contract.completed = false " +
            "and product.id = :productId " +
            "and simulation.isEffective = false")
    List<Simulation> listSimulationsFromProductAndClient(@Param("productId") Long productId, @Param("cpf")String cpf);

    @Query("from Simulation as simulation " +
            "join simulation.product as product " +
            "where simulation.isEffective = false " +
            "and simulation.installmentSize = :instalmentSize " +
            "and simulation.total = :value " +
            "and product.id = :productId ")
    List<Simulation> listWithSameValueAndNotEffective(@Param("instalmentSize") Integer installmentSize,
                                                      @Param("value") Double value,
                                                      @Param("productId") Long productId);

}
