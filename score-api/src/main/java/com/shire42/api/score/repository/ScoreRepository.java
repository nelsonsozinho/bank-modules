package com.shire42.api.score.repository;

import com.shire42.api.score.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("from Score as score where score.client.cpf = :cpf")
    Optional<Score> findByClientCpf(String cpf);

}
