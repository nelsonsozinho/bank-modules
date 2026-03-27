package com.shire42.api.bank.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transaction")
public class TransactionEntity {

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
