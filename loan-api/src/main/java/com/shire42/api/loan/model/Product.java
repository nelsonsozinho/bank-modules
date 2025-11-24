package com.shire42.api.loan.model;

import com.shire42.api.loan.model.enums.LoanType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "min_value", nullable = false, columnDefinition = "numeric(19,2) default 0")
    private Double minValue;

    @Column(name = "max_value", nullable = false, columnDefinition = "numeric(19,2) default 0")
    private Double maxValue;

    @OneToMany(mappedBy = "product")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "product")
    private List<Simulation> simulations;

}
