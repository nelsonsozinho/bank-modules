package com.shire42.api.availability.repository;

import com.shire42.api.availability.model.FinancialRestriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialRestrictionRepository extends JpaRepository<FinancialRestriction, Long> {

    @Query("from FinancialRestriction as f " +
            "inner join f.client.restrictions as r " +
            "where f.client.cpf = :cpf " +
            "and r.idActivated = true")
    List<FinancialRestriction> findByCpf(final String cpf);

}
