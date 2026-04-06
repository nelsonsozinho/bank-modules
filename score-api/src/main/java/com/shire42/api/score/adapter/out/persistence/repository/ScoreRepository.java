package com.shire42.api.score.adapter.out.persistence.repository;

import com.shire42.api.score.adapter.out.persistence.model.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {

    @Query("select score " +
            "from ScoreEntity score " +
            "join fetch score.client " +
            "where score.client.cpf = :cpf ")
    Optional<ScoreEntity> findByClientCpf(String cpf);

}
