package com.shire42.api.loan.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name="simulation")
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total", nullable = false, columnDefinition = "numeric(19,2) default 0")
    private Double total;

    @Column(name = "instalment_size")
    private Integer installmentSize;

    @Column(name = "is_effective")
    private Boolean isEffective;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @OneToMany(mappedBy = "simulation", fetch = FetchType.EAGER)
    private List<Installment> installments;

}
