package com.shire42.api.bank.domain.repository;

import com.shire42.api.bank.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Client, Long> {
}
