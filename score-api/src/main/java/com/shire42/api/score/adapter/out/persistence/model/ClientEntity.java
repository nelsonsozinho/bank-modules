package com.shire42.api.score.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="cpf", unique = true, length = 11)
    private String cpf;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private ScoreEntity score;

}
