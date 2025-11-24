package com.shire42.api.score.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id", unique = true, nullable = false)
    private Client client;

    @Column(name = "last_update")
    private LocalDate lastUpdate;

    @Column(name = "found_score", nullable = false, columnDefinition = "numeric(19,2) default 0")
    private Double score;

}
