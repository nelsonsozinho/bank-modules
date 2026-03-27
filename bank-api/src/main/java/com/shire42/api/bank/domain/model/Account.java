package com.shire42.api.bank.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {

    private Long id;
    private String number;
    private Client client;
    private Double balance;

}
