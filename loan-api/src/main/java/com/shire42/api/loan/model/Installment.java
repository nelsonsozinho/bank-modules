package com.shire42.api.loan.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="installment")
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="number", nullable = false)
    private Integer number;

    @Column(name = "value", nullable = false, columnDefinition = "numeric(19,2) default 0")
    private Double value;

    @Column(name = "discount", nullable = false, columnDefinition = "numeric(19,2) default 0")
    private Double discount;

    @Column(name = "total", nullable = false, columnDefinition = "numeric(19,2) default 0")
    private Double total;

    @Column(name = "total_with_discount", nullable = false, columnDefinition = "numeric(19,2) default 0")
    private Double totalWithDiscount;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "simulation_id")
    private Simulation simulation;

}
