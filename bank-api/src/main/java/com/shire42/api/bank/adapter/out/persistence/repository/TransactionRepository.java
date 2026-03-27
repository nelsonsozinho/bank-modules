package com.shire42.api.bank.adapter.out.persistence.repository;

import com.shire42.api.bank.adapter.out.persistence.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {}
