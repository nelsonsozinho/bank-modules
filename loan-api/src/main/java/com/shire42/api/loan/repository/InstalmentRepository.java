package com.shire42.api.loan.repository;

import com.shire42.api.loan.model.Installment;
import com.shire42.api.loan.model.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstalmentRepository extends JpaRepository<Installment, Long> {
}
