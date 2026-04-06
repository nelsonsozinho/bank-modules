package com.shire42.api.score.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "score")
public class ScoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id", unique = true, nullable = false)
    private ClientEntity client;

    @Column(name = "last_update")
    private LocalDate lastUpdate;

    @Column(name = "found_score", nullable = false, columnDefinition = "numeric(19,2) default 0")
    private Double score;

}
