package com.shire42.api.score.adapter.out.persistence.repository;

import com.shire42.api.score.adapter.out.persistence.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findClientByCpf(String cpf);

}
