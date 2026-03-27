package com.shire42.api.bank.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="cpf")
    private String cpf;

    @Column(name="email")
    private String email;

    @OneToMany(mappedBy = "client")
    private List<AccountEntity> accounts;

}
