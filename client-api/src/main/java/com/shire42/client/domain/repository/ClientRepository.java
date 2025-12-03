package com.shire42.client.domain.repository;

import com.shire42.client.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);
    Optional<Client> findByCpf(String rg);
    Optional<Client> findByRg(String cpf);

}
