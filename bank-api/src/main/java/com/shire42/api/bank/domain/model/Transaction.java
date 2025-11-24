package com.shire42.api.bank.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;

    private String sourceAccountNumber;

    private String targetAccountNumber;

    @Column(name = "amount", nullable = false, columnDefinition = "numeric(19,2) default 0")
    private Double amount;

    @Column(nullable = false)
    private String transactionType;

    @Column(nullable = false, name="transaction_data")
    private LocalDate date;

}
