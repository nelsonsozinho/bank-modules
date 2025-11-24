package com.shire42.api.loan.repository;

import com.shire42.api.loan.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("from Contract as c " +
            "inner join c.client client " +
            "inner join c.simulations simulation " +
            "where c.completed = false " +
            "and simulation.isEffective = false " +
            "and client.cpf = :cpf")
    List<Contract> findContractsActivatedYet(String cpf);


    @Query("from Contract as c " +
            "inner join c.client client " +
            "where c.completed = true " +
            "and c.isAssign = true " +
            "and client.cpf = :cpf")
    List<Contract> findContractsCompleted(String cpf);

}
